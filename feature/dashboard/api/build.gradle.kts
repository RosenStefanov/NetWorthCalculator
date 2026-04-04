plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.dashboard.api"
}

dependencies {
    implementation(project(":core:common"))
}