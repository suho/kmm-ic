import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
}

android {

    signingConfigs {
        val keystorePropertiesFile = rootProject.file("signing.properties")
        if (keystorePropertiesFile.exists()) {
            create("release") {
                val keystoreProperties = Properties()
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))
                storeFile = rootProject.file("config/release.keystore")
                storePassword = keystoreProperties.getProperty("ANDROID_SIGNING_KEYSTORE_PASSWORD")
                keyAlias = keystoreProperties.getProperty("ANDROID_SIGNING_KEY_ALIAS")
                keyPassword = keystoreProperties.getProperty("ANDROID_SIGNING_KEY_PASSWORD")
            }
        }

        getByName("debug") {
            storeFile = rootProject.file("config/debug.keystore")
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

    implementation(platform("com.google.firebase:firebase-bom:30.3.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}