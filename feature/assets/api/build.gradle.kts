plugins {
    id("networthcalculator.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.assets.api"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.kotlinx.serialization.json)
    api(libs.navigation3.runtime)
}
