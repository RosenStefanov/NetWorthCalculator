plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.core.common"
}

dependencies {
    api(libs.kotlinx.coroutines.core)
    api(libs.javax.inject)

    testImplementation(libs.junit5.api)
    testImplementation(libs.truth)
    testRuntimeOnly(libs.junit5.engine)
}