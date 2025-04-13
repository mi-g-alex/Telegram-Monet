plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.c3r5b8.telegram_monet"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.c3r5b8.telegram_monet"
        minSdk = 31
        targetSdk = 35
        versionCode = 25041301
        versionName = "11.7.0"

        resourceConfigurations.addAll(
            arrayOf(
                "ar", "bn_IN", "de", "es", "fa_IR", "fil", "fr", "hi", "hr", "in", "it", "iw",
                "kab", "ja", "ml", "nl", "pl", "pt", "pt_BR", "ro", "ru", "tl", "tr_TR", "uk_UA", "uz",
                "vi", "zh_CN"
            )
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlinOptions {
        jvmTarget = "18"
        freeCompilerArgs += arrayOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "**"
        }
    }

    dependenciesInfo.includeInApk = false
    dependenciesInfo.includeInBundle = false

    signingConfigs {
        getByName("debug") {
            storeFile = file(layout.buildDirectory.dir("../testkey.keystore"))
            storePassword = "testkey"
            keyAlias = "testkey"
            keyPassword = "testkey"
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.oneui6.material3.dynamic.color.compose)
    debugImplementation(libs.ui.tooling)
}
