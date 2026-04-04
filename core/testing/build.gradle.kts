plugins{
    id("networthcalculator.android.library")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
}