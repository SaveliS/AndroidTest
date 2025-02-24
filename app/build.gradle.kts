plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.sendy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sendy"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.12.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(files("lib/sendy_app_sdk.aar"))
    implementation(libs.core.ktx)
    implementation(libs.runtime.android)
    implementation(libs.foundation.android)
    implementation(libs.material3.android)
    implementation(libs.ui.tooling.preview.android)
    implementation(libs.activity)
    implementation(libs.annotation)
    implementation(libs.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}