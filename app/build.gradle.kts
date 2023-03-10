
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Libs.compileSdk

    defaultConfig {
        applicationId = "com.oncescaffold"
        minSdk = Libs.minSdk
        targetSdk = Libs.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        create("release") {
            keyAlias = "android"
            keyPassword = "android"
            storeFile = file("key.jks")
            storePassword = "android"
        }
    }
    buildTypes {
        release {
            // 启用代码压缩、优化及混淆
            isMinifyEnabled = true
            // 启用资源压缩，需配合 minifyEnabled=true 使用
            isShrinkResources = true
            // 指定混淆保留规则
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // 包签名
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Cversion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.oncescaffold"
}

dependencies {
    //不要一次性引入，否则不好移除
//    implementation fileTree(dir: '(Libs', include: ['*.jar', '*.aar'])


    implementation(Libs.AndroidX.coreKtx)

    implementation(Libs.AndroidX.multidex)

    implementation(Libs.AndroidX.Lifecycle.runtime)


    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.junitExt)
    androidTestImplementation(Libs.Test.espresso)


    //compose
    implementation(Libs.AndroidX.Activity.activityCompose)

    implementation(Libs.AndroidX.Compose.composeUI)

    implementation(Libs.AndroidX.Compose.material)

    implementation(Libs.AndroidX.Compose.runtimeLivedata)


    implementation(Libs.AndroidX.Compose.toolingPreview)
    debugImplementation(Libs.AndroidX.Compose.tooling)


    //官方组件库
    implementation (Libs.Google.Accompanist.accompanistInsets)

    implementation (Libs.Google.Accompanist.accompanistInsetsUI)

    implementation (Libs.Google.Accompanist.systemuicontroller)

    implementation (Libs.Google.Accompanist.pager)

    implementation (Libs.Google.Accompanist.navigationmaterial)

    implementation (Libs.Google.Accompanist.navigationanimation)

    implementation (Libs.Google.Accompanist.placeholdermaterial)

    implementation (Libs.Google.Accompanist.placeholder)

    implementation (Libs.AndroidX.Activity.constraintlayout)

    implementation (Libs.AndroidX.Lifecycle.viewModelCompose)

    implementation (Libs.AndroidX.Hilt.hiltNavigationCompose)

    implementation (Libs.AndroidX.Activity.pagingCompose)


    implementation (Libs.AndroidX.Hilt.hiltAndroid)
    kapt(Libs.AndroidX.Hilt.hiltCompiler)

    implementation (Libs.Google.Gson)

    implementation (Libs.Other.composeCoil)

    implementation (Libs.Squareup.okhttp3)
    implementation (Libs.Squareup.loggingInterceptor)

    implementation (Libs.Squareup.retrofit2)
    implementation (Libs.Squareup.converterGson)

    implementation(Libs.Other.loggingInterceptor) {
        exclude(group = "org.json", module = "json")
    }

    implementation (Libs.Other.composeRefreshlayout)

    debugImplementation (Libs.Other.getActivityLogcat)


    //openAI API
//    implementation "com.aallam.openai:openai-client:2.1.3"
//
//    implementation("io.ktor:ktor-client-core:2.2.3")
//    implementation("io.ktor:ktor-client-okhttp:2.2.3")



}