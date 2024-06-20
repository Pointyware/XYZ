
plugins {
    `kotlin-dsl`
}

group = "org.pointyware.xyz.buildlogic"

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("koin-dependency-injection-convention") {
            id = "org.pointyware.xyz.koin-dependency-injection-convention"
            version = "0.1.0"
            implementationClass = "org.pointyware.xyz.buildlogic.KoinDependencyInjectionConventionPlugin"
        }

        create("kmp-convention") {
            id = "org.pointyware.xyz.kmp-convention"
            version = "0.1.0"
            implementationClass = "org.pointyware.xyz.buildlogic.KmpTargetsConventionPlugin"
        }
    }
}
