plugins {
    id("networthcalculator.android.application")
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
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Core
    implementation(project(":core:common"))
    implementation(project(":core:database:impl"))
    implementation(project(":core:ui"))

    // Features
    implementation(project(":feature:dashboard:impl"))
    implementation(project(":feature:assets:impl"))
    implementation(project(":feature:liabilities:impl"))
    implementation(project(":feature:settings:impl"))
}
