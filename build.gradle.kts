buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependency.KOTLIN_GRADLE_PLUGIN)
        classpath(Dependency.GRADLE)
        classpath(Dependency.GOOGLE_SERVICES)
        classpath(Dependency.KOTLIN_SERIALIZATION)
        classpath(Dependency.BUILD_KONFIG)
        classpath(Dependency.REALM_GRADLE_PLUGIN)
    }
}

plugins {
    id(Plugin.DETEKT).version(Version.DETEKT)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

detekt {
    toolVersion = Version.DETEKT

    source = files(
        "android/src/main/java",
        "shared/src"
    )
    parallel = false
    config = files("detekt.yml")
    buildUponDefaultConfig = false
    disableDefaultRuleSets = false

    ignoreFailures = false

    ignoredBuildTypes = listOf(BuildType.RELEASE)
    ignoredFlavors = listOf(Flavor.PRODUCTION)
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        md.required.set(true)
    }
}
