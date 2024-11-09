plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.tuempresa.lifebook"
<<<<<<< HEAD
    compileSdk = 35
=======
    compileSdk = 34
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19

    defaultConfig {
        applicationId = "com.tuempresa.lifebook"
        minSdk = 27
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.google.material)
<<<<<<< HEAD
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.biometric)
    implementation(libs.com.google.gms.google.services.gradle.plugin)
    implementation(libs.gms.play.services.auth)
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}