plugins {
    id("networthcalculator.android.library")
    id("networthcalculator.android.library.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.core.navigation"
}

dependencies {
    api(libs.navigation3.runtime)
    // Used to persist the per-tab back stacks across config change / process death by
    // reflectively serializing each @Serializable NavKey (no knowledge of concrete routes).
    implementation(libs.kotlinx.serialization.json)

    testImplementation(project(":core:testing"))
}
