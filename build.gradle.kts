plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.android.junit5) apply false
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    parallel = true
    autoCorrect = false
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

ktlint {
    version.set("1.5.0")
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    filter {
        exclude("**/build/**")
    }
}

tasks.register<Copy>("installGitHook") {
    description = "Installs the pre-commit git hook from scripts/pre-commit"
    group = "verification"
    from("$rootDir/scripts/pre-commit")
    into("$rootDir/.git/hooks")
    filePermissions {
        user {
            read = true
            write = true
            execute = true
        }
        group {
            read = true
            execute = true
        }
        other {
            read = true
            execute = true
        }
    }
}

gradle.projectsEvaluated {
    tasks.register("jacocoCoverageVerification") {
        group = "verification"
        description = "Aggregate coverage verification across all leaf modules"
        dependsOn(
            subprojects
                .filter { it.childProjects.isEmpty() } // only leaf modules
                .filter { !it.path.contains(":testing") } // exclude core:testing
                .map { "${it.path}:jacocoDebugCoverageVerification" }
        )
    }

    tasks.register("jacocoFullReport") {
        group = "verification"
        description = "Aggregate coverage report across all leaf modules"
        dependsOn(
            subprojects
                .filter { it.childProjects.isEmpty() }
                .filter { !it.path.contains(":testing") }
                .map { "${it.path}:jacocoDebugTestReport" }
        )
    }
}
