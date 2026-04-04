pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NetWorthCalculator"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

// Core
include(":core:common")
include(":core:testing")
include(":core:database:api")
include(":core:database:impl")
include(":core:ui")

// Feature: Dashboard
include(":feature:dashboard:api")
include(":feature:dashboard:impl")

// Feature: Assets
include(":feature:assets:api")
include(":feature:assets:impl")

// Feature: Liabilities
include(":feature:liabilities:api")
include(":feature:liabilities:impl")

// Feature: Settings
include(":feature:settings:api")
include(":feature:settings:impl")
