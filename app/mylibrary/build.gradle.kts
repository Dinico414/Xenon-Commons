plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

group = "com.xenon.commons"
version = "1.7"

android {
    namespace = "com.xenon.mylibrary"

    compileSdk = 36

    defaultConfig {
        minSdk = 29


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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2025.05.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3)

    implementation(libs.reorderable)
    implementation(libs.androidx.material3.window.size.class1.android)
    implementation(libs.androidx.material3.adaptive)
    implementation(libs.haze)
    implementation(libs.haze.materials)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.animation.graphics)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.coil.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.mathparser.org.mxparser)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.register("printJavaVersion") {
    doLast {
        println("Java version used by Gradle: ${System.getProperty("java.version")}")
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = project.group.toString()
                artifactId = "mylibrary"
                version = project.version.toString()

                pom {
                    name.set("Xenon Commons")
                    description.set("A collection of reusable Android components")
                    url.set("https://github.com/yourusername/Xenon-Commons")
                }
            }
        }
    }
}