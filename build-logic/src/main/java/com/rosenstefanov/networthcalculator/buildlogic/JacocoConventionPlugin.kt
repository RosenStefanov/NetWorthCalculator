package com.rosenstefanov.networthcalculator.buildlogic

import com.android.build.api.dsl.CommonExtension
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
                "**/R.class",
                "**/R\$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*_HiltModules*.*",
                "**/*_Factory.*",
                "**/*_MembersInjector.*",
                "**/*_Impl.*",
                "**/*Module_*.*",
                "**/*Hilt_*.*",
                "**/*_GeneratedInjector.*",
                "**/hilt_aggregated_deps/**",
            )

            afterEvaluate {
                val classDirectories = files(
                    fileTree("${buildDir}/tmp/kotlin-classes/debug") {
                        exclude(jacocoExcludes)
                    },
                    fileTree("${buildDir}/intermediates/javac/debug") {
                        exclude(jacocoExcludes)
                    },
                )

                val sourceDirectories = files(
                    "${projectDir}/src/main/java",
                    "${projectDir}/src/main/kotlin",
                )

                val executionData = files(
                    "${buildDir}/jacoco/testDebugUnitTest.exec",
                )

                tasks.register<JacocoReport>("jacocoDebugTestReport") {
                    description = "Generates JaCoCo coverage report for debug unit tests"
                    group = "verification"
                    dependsOn("testDebugUnitTest")

                    this.classDirectories.setFrom(classDirectories)
                    this.sourceDirectories.setFrom(sourceDirectories)
                    this.executionData.setFrom(executionData)

                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                        csv.required.set(false)
                    }
                }

                val isTestingModule = path.contains(":testing")

                if (!isTestingModule) {
                    tasks.register<JacocoCoverageVerification>("jacocoDebugCoverageVerification") {
                        description = "Verifies JaCoCo coverage meets 80% threshold"
                        group = "verification"
                        dependsOn("jacocoDebugTestReport")

                        this.classDirectories.setFrom(classDirectories)
                        this.sourceDirectories.setFrom(sourceDirectories)
                        this.executionData.setFrom(executionData)

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
