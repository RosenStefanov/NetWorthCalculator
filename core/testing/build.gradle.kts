plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.core.testing"
}

dependencies {
    api(project(":core:common"))

    // JUnit 5
    api(libs.junit5.api)
    api(libs.junit5.params)
    runtimeOnly(libs.junit5.engine)

    // JUnit 4 backward compat
    runtimeOnly(libs.junit.vintage.engine)
    api(libs.junit)

    // Coroutines
    api(libs.kotlinx.coroutines.test)

    // Mocking & assertions
    api(libs.mockk)
    api(libs.turbine)
    api(libs.truth)
}
