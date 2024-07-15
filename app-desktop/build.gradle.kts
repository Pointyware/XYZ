plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:entities"))
    implementation(project(":core:interactors"))
    implementation(project(":core:local"))
    implementation(project(":core:navigation"))
    implementation(project(":core:remote"))
    implementation(project(":core:ui"))
    implementation(project(":core:view-models"))

    implementation(project(":feature:drive"))
    implementation(project(":feature:login"))
    implementation(project(":feature:ride"))

    implementation(project(":app-shared"))

    implementation(libs.kotlinx.dateTime)
    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
    implementation(compose.ui)
    implementation(compose.preview)
    implementation(compose.material3)
    implementation(compose.components.resources)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.xyz.desktop"
    generateResClass = always
}

compose.desktop {
    application {
        mainClass = "org.pointyware.xyz.desktop.MainKt"
    }
}
