plugins {
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.core.common"
}

dependencies {
    api(libs.kotlinx.coroutines.core)
}