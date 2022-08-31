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
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version(Version.DETEKT)
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

tasks.detekt {
    reports {
        xml {
            outputLocation.set(file("build/reports/detekt.xml"))
            required.set(true)
        }

        html {
            outputLocation.set(file("build/reports/detekt.html"))
            required.set(true)
        }
    }
}
