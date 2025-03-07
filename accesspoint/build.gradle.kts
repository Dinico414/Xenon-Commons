plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

group = "com.xenon.commons"
version = "1.0.8-2"

android {
    namespace = "com.xenon.commons.accesspoint"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

//    android {
//        publishing {
//            singleVariant("release") {
//                withSourcesJar()
//            }
//        }
//    }

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

publishing {
//    repositories {
//        maven {
//            name = "arcverseRepository"
//            url = uri("https://repo.arcver.se/private")
//            credentials(PasswordCredentials::class)
//            authentication {
//                create<BasicAuthentication>("basic")
//            }
//        }
//    }
    publications {
        create<MavenPublication>("release") {
            groupId = "com.xenon.commons"
            artifactId = "accesspoint"
            version = project.version.toString()
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}