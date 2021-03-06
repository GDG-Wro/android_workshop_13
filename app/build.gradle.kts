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

    signingConfigs {
        create("release") {
            keyAlias = "key"
            keyPassword = "gdgworkshop"
            storePassword = "gdgworkshop"
            storeFile = file("upload.jks")
        }
    }

    buildTypes {
        named("debug") {
            isPseudoLocalesEnabled = true
        }
        named("release") {
            signingConfig = signingConfigs.getByName("release")
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
        coreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("**/NOTICE")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.10")
    compileOnly("androidx.annotation:annotation:1.2.0-alpha01")
    implementation(Kotlin.stdLibJdk7)
    implementation(project(":networking"))
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.5")

    testImplementation("junit:junit:4.+")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

configurations.all {
    resolutionStrategy.force("androidx.annotation:annotation:1.2.0-alpha01")
}