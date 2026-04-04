plugins {
    id("networthcalculator.android.feature")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.dashboard.impl"
}

dependencies {
    implementation(project(":feature:dashboard:api"))
    implementation(project(":feature:assets:api"))
    implementation(project(":feature:liabilities:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))

    testImplementation(project(":core:testing"))
}