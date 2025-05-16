import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)




    kotlin("kapt") // Apply the kapt plugin

}




android {
    namespace = "com.ndichu.kulturekart"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ndichu.kulturekart"
        minSdk = 23
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
    buildFeatures {
        compose = true
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
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("io.coil-kt:coil-compose:2.4.0")
    // Hilt Android
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // For ViewModel integration (if you are using it with Hilt)
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0") // Or the latest version
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation ("com.google.dagger:hilt-android:2.48") // Use the latest version
    kapt( "com.google.dagger:hilt-android-compiler:2.48" ) // Use the latest version
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07" )



    //Navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.8.9")
    implementation("androidx.navigation:navigation-compose:2.8.9")
    //firebase
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.google.firebase:firebase-storage-ktx:20.3.0")


    implementation("androidx.compose.material3:material3:1.3.0-beta01") // Or the latest version

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Or the latest
    //Room Dependencies
    implementation ("androidx.room:room-runtime:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")


    implementation ("androidx.navigation:navigation-compose:2.7.4")
    implementation ("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.activity:activity-compose:1.7.2")


    //Material3
    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.material3:material3:1.2.1")


    // Image loading
    implementation("io.coil-kt:coil-compose:2.2.2")

// Accompanist Pager (image slider)
    implementation("com.google.accompanist:accompanist-pager:0.31.5-beta")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.31.5-beta")
    //storage
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    ////imager
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")


}