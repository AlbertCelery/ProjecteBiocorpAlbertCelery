plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.projectebiocorpalbertcelery"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projectebiocorpalbertcelery"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val navVersion = "2.7.1"
    val lifecycleVersion = "2.6.1"
    //NavComponent
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion") // Check for the latest version
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}