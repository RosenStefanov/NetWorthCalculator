plugins {
    id("networthcalculator.android.feature")
    id("networthcalculator.roborazzi")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.settings.impl"
}

dependencies {
    api(project(":feature:settings:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))

    testImplementation(project(":core:testing"))
}
