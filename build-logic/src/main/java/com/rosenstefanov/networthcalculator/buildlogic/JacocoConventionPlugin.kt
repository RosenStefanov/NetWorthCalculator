package com.rosenstefanov.networthcalculator.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("jacoco")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure(JacocoPluginExtension::class.java) {
                toolVersion = libs.findVersion("jacoco").get().requiredVersion
            }

            val jacocoExcludes = listOf(
                // Android / build generated
                "**/R.class",
                "**/R\$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                // Hilt generated
                "**/*_HiltModules*.*",
                "**/*_Factory.*",
                "**/*_MembersInjector.*",
                "**/*_Impl.*",
                "**/*Module_*.*",
                "**/*Hilt_*.*",
                "**/*_GeneratedInjector.*",
                "**/hilt_aggregated_deps/**",
                // Compose UI — exercised by instrumentation / snapshot tests, not JVM unit tests
                "**/*Screen.*",
                "**/*Screen\$*.*",
                "**/ComposableSingletons*.*",
                "**/RememberNavigatorKt.*",
                "**/ui/theme/**",
                // Navigation plumbing — declarative, no logic to cover
                "**/*Route.*",
                "**/*Routes.*",
                "**/*Navigation.*",
                "**/AppNavDisplay*.*",
                "**/NetWorthApp*.*",
                "**/FloatingBottomNavBar*.*",
                // Android framework entry points
                "**/MainActivity.*",
                "**/*Application.*",
            )

            val isTestingModule = path.contains(":testing")

            // Task registration must live inside afterEvaluate so the local collections
            // are correctly captured in the task configuration closures. A previous
            // version registered the verification task outside afterEvaluate, which
            // caused `classDirectories` to resolve to the task's own (empty) property
            // instead of the intended FileCollection — leaving verification vacuously
            // passing on every module.
            afterEvaluate {
                val classDirectoriesFiles = files(
                    fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
                        exclude(jacocoExcludes)
                    },
                    fileTree(layout.buildDirectory.dir("intermediates/javac/debug")) {
                        exclude(jacocoExcludes)
                    },
                )

                val sourceDirectoriesFiles = files(
                    layout.projectDirectory.dir("src/main/java"),
                    layout.projectDirectory.dir("src/main/kotlin"),
                )

                val executionDataFiles = files(
                    layout.buildDirectory.file("jacoco/testDebugUnitTest.exec"),
                )

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

                        // Override the JaCoCo plugin's default onlyIf that silently skips
                        // the task when no .exec file exists. We want verification to run
                        // whenever this module has compiled classes — that way modules
                        // with code but no tests hit the 80% gate and fail loudly instead
                        // of being invisibly skipped.
                        onlyIf {
                            val classDir = layout.buildDirectory
                                .dir("tmp/kotlin-classes/debug").get().asFile
                            classDir.exists() && classDir.walk().any { it.extension == "class" }
                        }

                        violationRules {
                            rule {
                                limit {
                                    counter = "INSTRUCTION"
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
