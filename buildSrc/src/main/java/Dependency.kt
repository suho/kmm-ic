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

    // Debug
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Version.COMPOSE_STABLE_VERSION}"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Version.COMPOSE_STABLE_VERSION}"

    // Tests
    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Version.JUNIT_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Version.ESPRESSO_CORE}"
    const val COMPOSE_UI_TEST_JUNIT = "androidx.compose.ui:ui-test-junit4:${Version.COMPOSE_STABLE_VERSION}"
}
