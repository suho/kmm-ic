import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
}

val keystoreProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "signing.properties")))
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("../config/release.keystore")
            storePassword = keystoreProperties.getProperty("KEYSTORE_PASSWORD")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
        }

        getByName("debug") {
            storeFile = file("../config/debug.keystore")
            storePassword = "oQ4mL1jY2uX7wD8q"
            keyAlias = "debug-key-alias"
            keyPassword = "oQ4mL1jY2uX7wD8q"
        }
    }

    compileSdk = 32
    defaultConfig {
        applicationId = "co.nimblehq.ic.kmm.suv.android"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "0.1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs["release"]
        }

        getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs["debug"]
        }
    }
    flavorDimensions += "version"
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"
        }
        create("production") {
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
}