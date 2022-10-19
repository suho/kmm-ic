object Flavor {
    const val PRODUCTION = "production"
    const val STAGING = "staging"
    const val DIMENSION = "version"
}

object BuildType {
    const val DEBUG = "debug"
    const val RELEASE = "release"
}

object Module {
    const val SHARED = ":shared"
    const val JSONAPI_CORE = ":jsonapi-kotlin:core"
}

object XcodeConfiguration {
    const val DEBUG_STAGING = "Debug Staging"
    const val DEBUG_PRODUCTION = "Debug Production"
    const val RELEASE_STAGING = "Release Staging"
    const val RELEASE_PRODUCTION = "Release Production"
}
