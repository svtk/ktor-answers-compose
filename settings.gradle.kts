rootProject.name = "KtorAnswersCompose"

include(":androidApp")
include(":shared")
include(":desktopApp")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("plugin.serialization").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)

        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)

        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    versionCatalogs {
        create("libs") {
            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            library("kotlinx-datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            library("voyager-navigator", "cafe.adriel.voyager:voyager-navigator:1.0.0-rc05")

            val ktor = "2.3.0"
            library("ktor-client-core", "io.ktor:ktor-client-core:$ktor")
            library("ktor-client-content-negotiation", "io.ktor:ktor-client-content-negotiation:$ktor")
            library("ktor-client-serialization", "io.ktor:ktor-client-serialization:$ktor")
            library("ktor-serialization-kotlinx-json", "io.ktor:ktor-serialization-kotlinx-json:$ktor")
            library("ktor-client-logging", "io.ktor:ktor-client-logging:$ktor")
            library("ktor-client-okhttp", "io.ktor:ktor-client-okhttp:$ktor")
            library("ktor-client-ios", "io.ktor:ktor-client-darwin:$ktor")

            val kotlinxCoroutines = "1.6.4" // update to 1.7.0 after KTOR-5728
            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutines")
            library("kotlinx-coroutines-swing", "org.jetbrains.kotlinx:kotlinx-coroutines-swing:$kotlinxCoroutines")
            library("kotlinx-coroutines-android", "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinxCoroutines")

            val koin = "3.4.0"
            library("koin-core", "io.insert-koin:koin-core:$koin")
            library("koin-android", "io.insert-koin:koin-android:$koin")
//            val koinCompose = "1.0.1"
//            library("koin-compose", "io.insert-koin:koin-compose:$koinCompose")

            // Android
            library("activity-compose", "androidx.activity:activity-compose:1.7.1")
            library("appcompat", "androidx.appcompat:appcompat:1.6.1")
            library("core-ktx", "androidx.core:core-ktx:1.10.1")
        }
    }
}
