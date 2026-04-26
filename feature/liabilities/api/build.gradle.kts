plugins {
    id("networthcalculator.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.liabilities.api"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.kotlinx.serialization.json)
    api(libs.navigation3.runtime)
}
