plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId = "pl.gdg.workshop13"
        minSdkVersion(21)
        targetSdkVersion(30)
        resConfigs("en")
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("debug") {
            isPseudoLocalesEnabled = true
        }
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    productFlavors {
        flavorDimensions("environment", "brand")

        create("development") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_URL", """"https://gdg.dev"""")
        }
        create("staging") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_URL", """"https://gdg.stg"""")
        }
        create("production") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_URL", """"https://gdg.prod"""")
        }

        create("gdg") {
            dimension = "brand"
            applicationIdSuffix = ".gdg"
        }
        create("asi") {
            dimension = "brand"
            applicationIdSuffix = ".asi"
        }
    }

    variantFilter {
        if (buildType.name == "release" && !name.contains("production", ignoreCase = true)) {
            ignore = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(Kotlin.stdLibJdk7)
    implementation(project(":networking"))
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}