plugins {
    id(Plugin.ANDROID_APPLICATION)
    id(Plugin.GOOGLE_SERVICE)
    kotlin(Plugin.ANDROID)
    id(Plugin.PARCELIZE)
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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Version.KOTLIN_JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Version.COMPOSE_VERSION
    }
    testOptions {
        animationsDisabled = true
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,*.md}"
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

    // Compose
    implementation(Dependency.CORE_KTX)
    implementation(Dependency.COMPOSE_UI)
    implementation(Dependency.COMPOSE_MATERIAL)
    implementation(Dependency.COMPOSE_UI_PREVIEW)
    implementation(Dependency.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependency.ACTIVITY_COMPOSE)
    implementation(Dependency.NAVIGATION_COMPOSE)
    implementation(Dependency.VIEW_MODEL_COMPOSE)
    implementation(Dependency.COIL_COMPOSE)
    implementation(Dependency.ACCOMPANIST_PLACEHOLDER)
    implementation(Dependency.ACCOMPANIST_PAGER)
    implementation(Dependency.ACCOMPANIST_SWIPE_REFRESH)
    implementation(Dependency.COMPOSE_CONSTRAINT_LAYOUT)
    implementation(Dependency.COMPOSE_UI_UTIL)
    implementation(Dependency.COMPOSE_LOTTIE)

    // Koin
    implementation(Dependency.KOIN_CORE)
    implementation(Dependency.KOIN_ANDROID)
    implementation(Dependency.KOIN_COMPOSE)

    // Date
    implementation(Dependency.DATE_TIME)

    // Debug
    implementation(Dependency.TIMBER)
    debugImplementation(Dependency.COMPOSE_UI_TOOLING)
    debugImplementation(Dependency.COMPOSE_UI_TEST_MANIFEST)

    // Test
    testImplementation(Dependency.JUNIT)
    testImplementation(Dependency.MOCKK)
    testImplementation(Dependency.KOTLIN_COROUTINES_TEST)
    testImplementation(Dependency.KOTEST_ASSERTIONS)
    androidTestImplementation(Dependency.MOCKK_ANDROID)
    androidTestImplementation(Dependency.JUNIT_EXT)
    androidTestImplementation(Dependency.ESPRESSO_CORE)
    androidTestImplementation(Dependency.COMPOSE_UI_TEST_JUNIT)
}
