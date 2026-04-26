plugins {
    id("networthcalculator.android.feature")
}

android {
    namespace = "com.rosenstefanov.networthcalculator.feature.dashboard.impl"
}

dependencies {
    api(project(":feature:dashboard:api"))
    implementation(project(":feature:assets:api"))
    implementation(project(":feature:liabilities:api"))
    implementation(project(":feature:settings:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))

    testImplementation(project(":core:testing"))
}
