import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin(Plugin.MULTIPLATFORM)
    kotlin(Plugin.COCOAPODS)
    id(Plugin.ANDROID_LIBRARY)
    kotlin(Plugin.KOTLIN_SERIALIZATION)
    id(Plugin.KOTLINX_SERIALIZATION)
    id(Plugin.NATIVE_COROUTINES).version(Version.NATIVE_COROUTINES_KOTLIN)
    id(Plugin.BUILD_KONFIG)
}

version = "1.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        name = "Shared"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "Shared"
        }
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.DEBUG_STAGING] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.DEBUG_PRODUCTION] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.RELEASE_STAGING] = NativeBuildType.RELEASE
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.RELEASE_PRODUCTION] = NativeBuildType.RELEASE
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(Dependency.COROUTINES_CORE)

                // Ktor
                implementation(Dependency.KTOR_CORE)
                implementation(Dependency.KTOR_SERIALIZATION)
                implementation(Dependency.KTOR_LOGGING)
                implementation(Dependency.KTOR_CIO)
                implementation(Dependency.KTOR_CONTENT_NEGOTIATION)
                implementation(Dependency.KTOR_JSON)

                // Logging
                implementation(Dependency.NAPIER)

                // Koin
                implementation(Dependency.KOIN_CORE)
                implementation(Dependency.KOIN_TEST)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependency.KTOR_ANDROID)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Dependency.KTOR_IOS)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = Version.ANDROID_COMPILE_SDK_VERSION
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Version.ANDROID_MIN_SDK_VERSION
        targetSdk = Version.ANDROID_TARGET_SDK_VERSION
    }
}

val buildKonfigProperties = rootDir.loadGradleProperties("buildKonfig.properties")

buildkonfig {
    packageName = "co.nimblehq.ic.kmm.suv"

    defaultConfigs {
        buildConfigField(
            STRING,
            "CLIENT_ID",
            buildKonfigProperties.getProperty("CLIENT_ID")
        )
        buildConfigField(
            STRING,
            "CLIENT_SECRET",
            buildKonfigProperties.getProperty("CLIENT_SECRET")
        )
        buildConfigField(
            STRING,
            "BASE_URL",
            buildKonfigProperties.getProperty("DEFAULT_BASE_URL")
        )
    }

    defaultConfigs(Flavor.PRODUCTION) {
        buildConfigField(
            STRING,
            "BASE_URL",
            buildKonfigProperties.getProperty("PRODUCTION_BASE_URL")
        )
    }

    defaultConfigs(Flavor.STAGING) {
        buildConfigField(
            STRING,
            "BASE_URL",
            buildKonfigProperties.getProperty("STAGING_BASE_URL")
        )
    }
}
