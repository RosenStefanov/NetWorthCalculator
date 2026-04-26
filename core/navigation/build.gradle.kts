plugins {
    id("networthcalculator.android.library")
    id("networthcalculator.android.library.compose")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.core.navigation"
}

dependencies {
    api(libs.navigation3.runtime)

    testImplementation(project(":core:testing"))
}
