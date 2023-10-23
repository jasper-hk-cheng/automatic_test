plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "tw.com.jasper.automatic_test"
    compileSdk = 33

    defaultConfig {
        applicationId = "tw.com.jasper.automatic_test"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        // test
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
        // for Instrumentation on JUnit 5
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    // for instrumentation 的奇怪校正
    // packagingOptions {
    //     resources {
    //         excludes += ["META-INF/LICENSE.md", "META-INF/LICENSE-notice.md"]
    //     }
    // }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    testOptions {
        // for JUnit5
        // unitTests.all {
        //     useJUnitPlatform()
        // }
        // for robolectric
        // unitTests.includeAndroidResources = true

        // FIXME for mockk:
        //  MockK could not self-attach a jvmti agent to the current VM. This feature is required for inline mocking.
        //  Potentially, the current VM does not support the jvmti API correctly
        // packagingOptions {
        //     jniLibs {
        //         useLegacyPackaging = true
        //     }
        // }
    }

    // productFlavors {
    //     getByName("dev") {
    //         applicationId = "tw.com.japser.automatic_test" + name
    //         resValue("string", "flavor_name", name)
    //         manifestPlaceholders["app_name"] = "jasper " + name
    //         // signingConfig signingConfigs.mmbDebug
    //         matchingFallbacks = ["develop"]
    //     }
    //     ut {
    //         applicationId = "tw.com.japser.automatic_test" + name
    //         resValue("string", "flavor_name", name)
    //         manifestPlaceholders["app_name"] = "jasper " + name
    //         // signingConfig signingConfigs.mmbDebug
    //         matchingFallbacks = ["develop"]
    //     }
    // }

    // sourceSets {
    //     test {
    //         resources {
    //             srcDirs += ["src/testDev/assets"]
    //         }
    //     }
    // }
}

dependencies {
    // androidx
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    // view
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.test.espresso:espresso-idling-resource:3.5.1")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

    // rules and runner
    androidTestImplementation("com.android.support.test:rules:1.0.2")
    androidTestImplementation("com.android.support.test:runner:1.0.2")

    // android test
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")

    // android test - for JUnit 5
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    androidTestRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    androidTestRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")
    androidTestImplementation("org.junit.platform:junit-platform-launcher:1.9.2")
    androidTestImplementation("org.junit.platform:junit-platform-runner:1.9.2")
    androidTestImplementation("org.junit.platform:junit-platform-engine:1.9.2")
    androidTestImplementation("org.junit.platform:junit-platform-commons:1.9.2")  // implement
    androidTestImplementation("org.junit.platform:junit-platform-suite-api:1.9.2")
    androidTestImplementation("de.mannodermaus.junit5:android-test-core:1.3.0")
    androidTestRuntimeOnly("de.mannodermaus.junit5:android-test-runner:1.3.0")

    // unit test
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("androidx.test:rules:1.5.0")
    testImplementation("androidx.test:runner:1.5.2")
    testImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    testImplementation("androidx.test.espresso:espresso-intents:3.5.1")

    // unit test - JUnit5
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.9.2")
    testImplementation("org.junit.platform:junit-platform-runner:1.9.2")
    testImplementation("org.junit.platform:junit-platform-engine:1.9.2")
    testImplementation("org.junit.platform:junit-platform-commons:1.9.2") // implement
    testImplementation("org.junit.platform:junit-platform-suite-api:1.9.2")
    testImplementation("de.mannodermaus.junit5:android-test-core:1.3.0")
    testRuntimeOnly("de.mannodermaus.junit5:android-test-runner:1.3.0")

    // timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // mockk
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("io.mockk:mockk-android:1.12.0")
    androidTestImplementation("io.mockk:mockk-android:1.12.0")
    // reflector - 原為 runtimeOnly
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.22")
    // robolectric
    testImplementation("org.robolectric:robolectric:4.9")
    // testImplementation "org.robolectric:shadows-multidex:3.4-rc2" // 如果有使用multidex就要加這個

    // for test about lifecycle owner
    testImplementation("android.arch.core:core-testing:1.1.1")

    // gson
    implementation("com.google.code.gson:gson:2.8.9")

    // 目前沒用到
    // testImplementation 'androidx.test:core-ktx:1.5.0' // launchActivity()
    // testImplementation "androidx.fragment:fragment-testing:1.5.6"
}
