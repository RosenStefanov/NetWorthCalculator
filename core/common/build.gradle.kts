plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.common"
}

dependencies {
    api(libs.kotlinx.coroutines.core)
}