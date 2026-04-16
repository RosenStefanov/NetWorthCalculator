plugins{
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.testing"
}

dependencies {
    implementation(project(":core:common"))

    // JUnit 5
    api(libs.junit5.api)
    api(libs.junit5.params)
    runtimeOnly(libs.junit5.engine)

    // JUnit 4 backward compat
    runtimeOnly(libs.junit.vintage.engine)
    implementation(libs.junit)

    // Coroutines
    implementation(libs.kotlinx.coroutines.test)

    // Mocking & assertions
    implementation(libs.mockk)
    implementation(libs.turbine)
    implementation(libs.truth)
}
