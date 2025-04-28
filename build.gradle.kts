// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    val kotlin_version = "2.1.0"
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        val nav_version = "2.8.6"

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.android.tools.build:gradle:8.5.2")
    }
}

plugins {
    val room_version = "2.6.1"
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id("androidx.room") version "$room_version" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.55" apply false
    alias(libs.plugins.google.gms.google.services) apply false


}