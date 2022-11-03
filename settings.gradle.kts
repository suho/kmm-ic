pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Surveys"
include(":android")
include(":shared")
include(":jsonapi-kotlin:core")
