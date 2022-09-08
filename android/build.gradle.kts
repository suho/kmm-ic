plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
}

val keystoreProperties = rootDir.loadGradleProperties("signing.properties")

android {

    signingConfigs {
        create(BuildType.RELEASE) {
            storeFile = rootProject.file("config/release.keystore")
            storePassword = keystoreProperties.getProperty("ANDROID_SIGNING_KEYSTORE_PASSWORD")
            keyAlias = keystoreProperties.getProperty("ANDROID_SIGNING_KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("ANDROID_SIGNING_KEY_PASSWORD")
        }

        getByName(BuildType.DEBUG) {
            storeFile = rootProject.file("config/debug.keystore")
            storePassword = "oQ4mL1jY2uX7wD8q"
            keyAlias = "debug-key-alias"
            keyPassword = "oQ4mL1jY2uX7wD8q"
        }
    }

    compileSdk = Version.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        applicationId = "co.nimblehq.ic.kmm.suv.android"
        minSdk = Version.ANDROID_MIN_SDK_VERSION
        targetSdk = Version.ANDROID_TARGET_SDK_VERSION
        versionCode = Version.ANDROID_VERSION_CODE
        versionName = Version.ANDROID_VERSION_NAME
    }
    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = false
            signingConfig = signingConfigs[BuildType.RELEASE]
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = false
            signingConfig = signingConfigs[BuildType.DEBUG]
        }
    }
    flavorDimensions += Flavor.DIMENSION
    productFlavors {
        create(Flavor.STAGING) {
            applicationIdSuffix = ".staging"
        }
        create(Flavor.PRODUCTION) {
        }
    }
}

dependencies {
    implementation(project(Module.SHARED))

    implementation(platform(Dependency.FIREBASE_BOM))
    implementation(Dependency.FIREBASE_ANALYTICS)
    implementation(Dependency.MATERIAL)
    implementation(Dependency.APPCOMPAT)
    implementation(Dependency.CONSTRAINT_LAYOUT)
}
