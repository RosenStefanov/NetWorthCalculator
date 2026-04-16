plugins {
    `kotlin-dsl`
}

group = "com.rosenstefanov.networthcalculator.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.serialization.gradlePlugin)
    compileOnly(libs.androidJunit5.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "networthcalculator.android.application"
            implementationClass = "com.rosenstefanov.networthcalculator.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "networthcalculator.android.library"
            implementationClass = "com.rosenstefanov.networthcalculator.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "networthcalculator.android.feature"
            implementationClass = "com.rosenstefanov.networthcalculator.buildlogic.AndroidFeatureConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "networthcalculator.android.library.compose"
            implementationClass = "com.rosenstefanov.networthcalculator.buildlogic.AndroidLibraryComposeConventionPlugin"
        }
        register("kotlinAndroid") {
            id = "networthcalculator.kotlin.android"
            implementationClass = "com.rosenstefanov.networthcalculator.buildlogic.KotlinAndroidConventionPlugin"
        }
        register("jacoco") {
            id = "networthcalculator.jacoco"
            implementationClass = "com.rosenstefanov.networthcalculator.buildlogic.JacocoConventionPlugin"
        }
    }
}
