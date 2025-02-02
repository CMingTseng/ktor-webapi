pluginManagement {
    val android_gradle_plugin_version: String by settings
    val kotlin_version: String by settings
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven {
            url = uri("https://repo.maven.apache.org/maven2/")
        }
        maven {// Only required if using EAP releases
            url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenLocal()
    }

    plugins {
        id("com.android.library") version android_gradle_plugin_version
        id("com.android.application") version android_gradle_plugin_version
//        id("org.jetbrains.kotlin.jvm") version kotlin_version
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin") || requested.id.id == "org.jetbrains.kotlin" || requested.id.namespace == "org.jetbrains.kotlin" || requested.id.id == "kotlin-multiplatform" || requested.id.id == "org.jetbrains.kotlin.multiplatform" || requested.id.id == "kotlin-dce-js") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven {
            url = uri("https://repo.maven.apache.org/maven2/")
        }
        maven {// Only required if using EAP releases
            url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenLocal()
        gradlePluginPortal()
    }
}

rootProject.name = "ktor-webapi"
include(
    "webapi",
    "ktor_unit",
    "Ktor_at_Android"
)