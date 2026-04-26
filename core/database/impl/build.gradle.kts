plugins {
    id("networthcalculator.android.library")
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.database.impl"
}

dependencies {
    implementation(project(":core:database:api"))
    implementation(project(":core:common"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
