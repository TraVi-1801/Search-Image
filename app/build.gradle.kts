import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import java.util.Properties

plugins {
    alias(lib.plugins.android.application)
    alias(lib.plugins.kotlin.android)
    alias(lib.plugins.devtools.ksp)
    alias(lib.plugins.hilt.gradle)
    alias(lib.plugins.compose.compiler)
    alias(lib.plugins.room)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlinx.serialization)
}

// Load local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

// Read your key
val apiKey = localProperties["API_KEY"] as String

android {
    namespace = "com.vic_project.search_image"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vic_project.search_image"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs += listOf(
                "-Xcontext-receivers"
            )
        }


    buildFeatures {
        compose = true
    }

    composeCompiler {
        enableStrongSkippingMode = true
    }
}

extensions.configure<KspExtension> {
    arg("room.generateKotlin", "true")
}

extensions.configure<RoomExtension> {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.bcrypt)
    implementation(lib.androidx.core.ktx)
    implementation(lib.androidx.compose.bom)
    implementation(lib.compose.ui.tooling.preview)
    implementation(libs.androidx.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.barcode.scanning)//constraintlayout in compose
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.room.gradle.plugin)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(lib.lifecycle.runtime.ktx)
    implementation(lib.activity.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.ui.tooling.preview.android)
    testImplementation(lib.junit)
    androidTestImplementation(lib.test.ext.junit)
    androidTestImplementation(lib.espresso.core)
    implementation (libs.dagger.hilt.android)
    ksp (libs.dagger.hilt.compiler)
    implementation(lib.retrofit)
    implementation(lib.okhttp)
    implementation(lib.converter.gson)
    implementation(lib.logging.interceptor)
    implementation(lib.lottie.compose)
    implementation(lib.coil.compose)
    implementation(libs.coil.kt.gif)
    implementation(lib.room.runtime)
    implementation(lib.room.ktx)
    ksp(lib.room.compiler)
    implementation(lib.timber)
    implementation(lib.gson)
    implementation(lib.accompanist.pager)
    implementation(lib.accompanist.pager.indicators)
    implementation(lib.androidx.startup.runtime)
    implementation(libs.androidx.material3)
    implementation (libs.msal)
    implementation(libs.androidx.biometric)
    //accompanist permission
    implementation(libs.accompanist.permissions)
    implementation(libs.kotlinx.serialization.json)
}