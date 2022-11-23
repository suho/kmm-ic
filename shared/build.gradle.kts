import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

plugins {
    kotlin(Plugin.MULTIPLATFORM)
    kotlin(Plugin.COCOAPODS)
    id(Plugin.ANDROID_LIBRARY)
    kotlin(Plugin.KOTLIN_SERIALIZATION)
    id(Plugin.KOTLINX_SERIALIZATION)
    id(Plugin.NATIVE_COROUTINES).version(Version.NATIVE_COROUTINES_KOTLIN)
    id(Plugin.BUILD_KONFIG)
    id(Plugin.KSP).version(Version.KSP)
    id(Plugin.REALM)
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
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.DEBUG_STAGING] =
            NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.DEBUG_PRODUCTION] =
            NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.RELEASE_STAGING] =
            NativeBuildType.RELEASE
        xcodeConfigurationToNativeBuildType[XcodeConfiguration.RELEASE_PRODUCTION] =
            NativeBuildType.RELEASE
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Core
                implementation(Dependency.COROUTINES_CORE)

                // Ktor
                implementation(Dependency.KTOR_CORE)
                implementation(Dependency.KTOR_SERIALIZATION)
                implementation(Dependency.KTOR_LOGGING)
                implementation(Dependency.KTOR_CIO)
                implementation(Dependency.KTOR_CONTENT_NEGOTIATION)
                implementation(Dependency.KTOR_JSON)
                implementation(Dependency.KTOR_AUTH)

                // Logging
                implementation(Dependency.NAPIER)

                // Koin
                implementation(Dependency.KOIN_CORE)
                implementation(Dependency.KOIN_TEST)

                // jsonapi
                implementation(project(Module.JSONAPI_CORE))

                // settings
                implementation(Dependency.SETTINGS)
                implementation(Dependency.SETTINGS_SERIALIZATION)

                // Date
                implementation(Dependency.DATE_TIME)

                // Realm
                implementation(Dependency.REALM_LIBRARY_BASE)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(Dependency.COROUTINES_TEST)
                implementation(Dependency.MOCKATIVE)
                implementation(Dependency.KOTEST_FRAMEWORK)
                implementation(Dependency.KOTEST_ASSERTIONS)
                implementation(Dependency.KOTEST_PROPERTY)
                implementation(Dependency.TURBINE)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependency.KTOR_ANDROID)
                implementation(Dependency.SECURITY_CRYPTO_KTX)
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

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, Dependency.MOCKATIVE_PROCESSOR)
        }
}

ksp {
    arg("mockative.stubsUnitByDefault", "true")
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
            buildKonfigProperties.getProperty("STAGING_CLIENT_ID")
        )
        buildConfigField(
            STRING,
            "CLIENT_SECRET",
            buildKonfigProperties.getProperty("STAGING_CLIENT_SECRET")
        )
        buildConfigField(
            STRING,
            "BASE_URL",
            buildKonfigProperties.getProperty("STAGING_BASE_URL")
        )
    }

    defaultConfigs(Flavor.PRODUCTION) {
        buildConfigField(
            STRING,
            "CLIENT_ID",
            buildKonfigProperties.getProperty("PRODUCTION_CLIENT_ID")
        )
        buildConfigField(
            STRING,
            "CLIENT_SECRET",
            buildKonfigProperties.getProperty("PRODUCTION_CLIENT_SECRET")
        )
        buildConfigField(
            STRING,
            "BASE_URL",
            buildKonfigProperties.getProperty("PRODUCTION_BASE_URL")
        )
    }

    defaultConfigs(Flavor.STAGING) {
        buildConfigField(
            STRING,
            "CLIENT_ID",
            buildKonfigProperties.getProperty("STAGING_CLIENT_ID")
        )
        buildConfigField(
            STRING,
            "CLIENT_SECRET",
            buildKonfigProperties.getProperty("STAGING_CLIENT_SECRET")
        )
        buildConfigField(
            STRING,
            "BASE_URL",
            buildKonfigProperties.getProperty("STAGING_BASE_URL")
        )
    }
}

tasks.withType<com.google.devtools.ksp.gradle.KspTask>().configureEach {
    when (this) {
        is com.google.devtools.ksp.gradle.KspTaskNative -> {
            this.compilerPluginOptions.addPluginArgument(
                tasks
                    .named<KotlinNativeCompile>(compilation.compileKotlinTaskName)
                    .get()
                    .compilerPluginOptions
            )
        }
    }
}
