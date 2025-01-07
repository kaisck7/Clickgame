plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // Plugin Compose
    id("com.google.gms.google-services") // Plugin Google Services pour Firebase
}

android {
    namespace = "com.example.clickgame"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.clickgame"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    // Firebase BOM pour gérer les versions Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Services Firebase nécessaires
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("androidx.compose.material3:material3:1.2.0")

    // Android Jetpack
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.8.4")
    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.runtime:runtime:1.5.0")
    implementation("androidx.compose.ui:ui-tooling:1.5.3") // Pour outils UI
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
}
