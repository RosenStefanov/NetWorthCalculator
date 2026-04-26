plugins {
    id("networthcalculator.android.library")
    id("networthcalculator.android.library.compose")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.ui"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
}
