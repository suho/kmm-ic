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

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
