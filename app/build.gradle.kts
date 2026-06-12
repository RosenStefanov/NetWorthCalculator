plugins {
    id("networthcalculator.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.rosenstefanov.networthcalculator"

    defaultConfig {
        applicationId = "com.rosenstefanov.networthcalculator"
        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation 3
    implementation(libs.navigation3.runtime)
    implementation(libs.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.hilt.navigation.compose)

    // Core
    implementation(project(":core:common"))
    implementation(project(":core:database:impl"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))

    // Features
    implementation(project(":feature:dashboard:impl"))
    implementation(project(":feature:assets:impl"))
    implementation(project(":feature:liabilities:impl"))
    implementation(project(":feature:settings:impl"))
}
