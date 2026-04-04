plugins {
    id("networthcalculator.android.feature")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.assets.impl"
}

dependencies {
    implementation(project(":feature:assets:api"))
    implementation(project(":core:database:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))

    testImplementation(project(":core:testing"))
}