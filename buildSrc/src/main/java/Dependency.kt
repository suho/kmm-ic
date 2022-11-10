object Dependency {
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.KOTLIN_VERSION}"
    const val GRADLE = "com.android.tools.build:gradle:${Version.BUILD_GRADLE_VERSION}"
    const val GOOGLE_SERVICES = "com.google.gms:google-services:${Version.GOOGLE_SERVICES_VERSION}"
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Version.FIREBASE_BOM}"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx:${Version.FIREBASE_ANALYTICS}"
    const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.APPCOMPAT}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val COMPOSE_UI = "androidx.compose.ui:ui:${Version.COMPOSE_STABLE_VERSION}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Version.COMPOSE_STABLE_VERSION}"
    const val COMPOSE_UI_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Version.COMPOSE_STABLE_VERSION}"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFECYCLE_RUNTIME_KTX}"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Version.ACTIVITY_COMPOSE}"
    const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:${Version.NAVIGATION_COMPOSE}"
    const val VIEW_MODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.VIEW_MODEL_COMPOSE}"
    const val COIL_COMPOSE = "io.coil-kt:coil-compose:${Version.COIL_COMPOSE}"


    // Kotlinx
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES_CORE}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.COROUTINES_CORE}"
    const val DATE_TIME = "org.jetbrains.kotlinx:kotlinx-datetime:${Version.DATE_TIME}"

    // Ktor
    const val KTOR_CORE = "io.ktor:ktor-client-core:${Version.KTOR}"
    const val KTOR_SERIALIZATION = "io.ktor:ktor-client-serialization:${Version.KTOR}"
    const val KTOR_LOGGING = "io.ktor:ktor-client-logging:${Version.KTOR}"
    const val KTOR_CIO = "io.ktor:ktor-client-cio:${Version.KTOR}"
    const val KTOR_CONTENT_NEGOTIATION = "io.ktor:ktor-client-content-negotiation:${Version.KTOR}"
    const val KTOR_JSON = "io.ktor:ktor-serialization-kotlinx-json:${Version.KTOR}"
    const val KTOR_AUTH = "io.ktor:ktor-client-auth:${Version.KTOR}"
    const val KTOR_ANDROID = "io.ktor:ktor-client-android:${Version.KTOR}"
    const val KTOR_IOS = "io.ktor:ktor-client-ios:${Version.KTOR}"

    // Log
    const val NAPIER = "io.github.aakira:napier:2.4.0"

    // Koin
    const val KOIN_CORE = "io.insert-koin:koin-core:${Version.KOIN}"
    const val KOIN_ANDROID = "io.insert-koin:koin-android:${Version.KOIN_ANDROID}"
    const val KOIN_COMPOSE = "io.insert-koin:koin-androidx-compose:${Version.KOIN_ANDROID}"
    const val KOIN_TEST = "io.insert-koin:koin-test:${Version.KOIN}"

    // Serialization
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlin:kotlin-serialization:${Version.KOTLIN_SERIALIZATION}"

    // Debug
    const val TIMBER = "com.jakewharton.timber:timber:${Version.TIMBER}"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Version.COMPOSE_STABLE_VERSION}"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Version.COMPOSE_STABLE_VERSION}"

    // BuildKonfig
    const val BUILD_KONFIG = "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:${Version.BUILD_KONFIG}"

    // Settings
    const val SETTINGS = "com.russhwolf:multiplatform-settings:${Version.SETTINGS}"
    const val SETTINGS_SERIALIZATION = "com.russhwolf:multiplatform-settings-serialization:${Version.SETTINGS}"
    const val SECURITY_CRYPTO_KTX = "androidx.security:security-crypto-ktx:${Version.SECURITY_CRYPTO}"

    // Tests
    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Version.JUNIT_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Version.ESPRESSO_CORE}"
    const val COMPOSE_UI_TEST_JUNIT = "androidx.compose.ui:ui-test-junit4:${Version.COMPOSE_STABLE_VERSION}"
    const val MOCKATIVE = "io.mockative:mockative:${Version.MOCKATIVE}"
    const val MOCKATIVE_PROCESSOR = "io.mockative:mockative-processor:${Version.MOCKATIVE}"
    const val KOTEST_FRAMEWORK = "io.kotest:kotest-framework-engine:${Version.KOTEST}"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core:${Version.KOTEST}"
    const val KOTEST_PROPERTY = "io.kotest:kotest-property:${Version.KOTEST}"
    const val MOCKK = "io.mockk:mockk:${Version.MOCKK}"
    const val KOTLIN_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.COROUTINES_CORE}"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:${Version.MOCKK}"
}
