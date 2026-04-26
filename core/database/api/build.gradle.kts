plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.database.api"
}

dependencies {
    implementation(project(":core:common"))
}
