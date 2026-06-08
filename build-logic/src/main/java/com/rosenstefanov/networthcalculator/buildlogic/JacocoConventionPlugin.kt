package com.rosenstefanov.networthcalculator.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withGroovyBuilder
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("jacoco")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val jacocoVersion = libs.findVersion("jacoco").get().requiredVersion

            extensions.configure(JacocoPluginExtension::class.java) {
                toolVersion = jacocoVersion
            }

            val jacocoAntConfig = configurations.getByName("jacocoAnt")
            val jacocoAgentRuntimeConfig = configurations.maybeCreate("jacocoAgentRuntime")
            dependencies {
                add("jacocoAgentRuntime", "org.jacoco:org.jacoco.agent:$jacocoVersion:runtime")
            }

            val jacocoExcludes = listOf(
                // Build-generated
                "**/R.class",
                "**/R\$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                // Hilt-generated
                "**/*_HiltModules*.*",
                "**/*_Factory.*",
                "**/*_MembersInjector.*",
                "**/*_Impl.*",
                "**/*Module_*.*",
                "**/*Hilt_*.*",
                "**/*_GeneratedInjector.*",
                "**/hilt_aggregated_deps/**",
                // Compose scaffolding (no logic)
                "**/ComposableSingletons*.*",
                "**/RememberNavigatorKt*",
                "**/ui/theme/**",
                "**/*RouteKt*",
                "**/*RoutesKt*",
                "**/*NavigationKt*",
                "**/AppNavDisplayKt*",
                "**/NetWorthAppKt*",
                "**/FloatingBottomNavBar*",
                // Framework entry points
                "**/MainActivity*",
                "**/*Application*",
            )

            val isTestingModule = path.contains(":testing")

            afterEvaluate {
                val classesDir = layout.buildDirectory.dir("tmp/kotlin-classes/debug")
                val instrumentedDir = layout.buildDirectory.dir("jacoco/instrumented/debug")
                val execFile = layout.buildDirectory.file("jacoco/testDebugUnitTest.exec")

                val instrumentTask = tasks.register("jacocoInstrumentDebug") {
                    group = "verification"
                    description = "JaCoCo offline-instruments debug classes for Robolectric coverage."
                    dependsOn("compileDebugKotlin")
                    // Declare the compiled classes as an input so the task re-instruments when
                    // code changes. Without this, Gradle sees only the output dir and treats the
                    // task as up-to-date, leaving STALE instrumented classes on the test classpath
                    // (causes NoSuchMethodError or phantom low coverage after edits).
                    inputs.dir(classesDir)
                        .withPropertyName("classesToInstrument")
                        .withPathSensitivity(PathSensitivity.RELATIVE)
                    outputs.dir(instrumentedDir)
                    onlyIf {
                        val src = classesDir.get().asFile
                        src.exists() && src.walk().any { it.extension == "class" }
                    }
                    doLast {
                        val srcDir = classesDir.get().asFile
                        val outDir = instrumentedDir.get().asFile
                        outDir.deleteRecursively()
                        outDir.mkdirs()
                        ant.withGroovyBuilder {
                            "taskdef"(
                                "name" to "instrument",
                                "classname" to "org.jacoco.ant.InstrumentTask",
                                "classpath" to jacocoAntConfig.asPath,
                            )
                            "instrument"("destdir" to outDir.absolutePath) {
                                "fileset"("dir" to srcDir.absolutePath)
                            }
                        }
                    }
                }

                tasks.named("testDebugUnitTest", Test::class.java).configure {
                    dependsOn(instrumentTask)
                    extensions.findByType(JacocoTaskExtension::class.java)?.isEnabled = false
                    classpath = files(instrumentedDir) + classpath + jacocoAgentRuntimeConfig
                    systemProperty(
                        "jacoco-agent.destfile",
                        execFile.get().asFile.absolutePath,
                    )
                }
                val classDirectoriesFiles = files(
                    fileTree(classesDir) { exclude(jacocoExcludes) },
                    fileTree(layout.buildDirectory.dir("intermediates/javac/debug")) {
                        exclude(jacocoExcludes)
                    },
                )

                val sourceDirectoriesFiles = files(
                    layout.projectDirectory.dir("src/main/java"),
                    layout.projectDirectory.dir("src/main/kotlin"),
                )

                val executionDataFiles = files(execFile)

                tasks.register<JacocoReport>("jacocoDebugTestReport") {
                    description = "Generates JaCoCo coverage report for debug unit tests"
                    group = "verification"
                    dependsOn("testDebugUnitTest")

                    this.classDirectories.setFrom(classDirectoriesFiles)
                    this.sourceDirectories.setFrom(sourceDirectoriesFiles)
                    this.executionData.setFrom(executionDataFiles)

                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                        csv.required.set(false)
                    }
                }

                if (!isTestingModule) {
                    tasks.register<JacocoCoverageVerification>("jacocoDebugCoverageVerification") {
                        description = "Verifies JaCoCo coverage meets 80% threshold"
                        group = "verification"
                        dependsOn("testDebugUnitTest")
                        dependsOn("jacocoDebugTestReport")

                        this.classDirectories.setFrom(classDirectoriesFiles)
                        this.sourceDirectories.setFrom(sourceDirectoriesFiles)
                        this.executionData.setFrom(executionDataFiles)

                        onlyIf {
                            val classDir = classesDir.get().asFile
                            classDir.exists() && classDir.walk().any { it.extension == "class" }
                        }

                        violationRules {
                            rule {
                                limit {
                                    counter = "LINE"
                                    value = "COVEREDRATIO"
                                    minimum = "0.80".toBigDecimal()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
