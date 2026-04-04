plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.settings.api"
}

dependencies {
    implementation(project(":core:common"))
}