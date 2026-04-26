plugins {
    id("networthcalculator.android.feature")
    id("networthcalculator.roborazzi")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.liabilities.impl"
}

dependencies {
    api(project(":feature:liabilities:api"))
    implementation(project(":feature:settings:api"))
    implementation(project(":core:database:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))

    testImplementation(project(":core:testing"))
}
