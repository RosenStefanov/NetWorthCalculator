plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.assets.api"
}

dependencies {
    implementation(project(":core:common"))
}