plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.liabilities.api"
}

dependencies {
    implementation(project(":core:common"))
}