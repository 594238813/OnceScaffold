

object Libs {

    const val compileSdk = 33

    const val minSdk = 26

    const val targetSdk = 33

    const val Cversion = "1.3.2"

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.4.2"

    const val daggerHiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:2.45"

    object Kotlin {

    }

    object AndroidX{

        const val coreKtx = "androidx.core:core-ktx:1.9.0"

        const val multidex = "androidx.multidex:multidex:2.0.1"


        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.5.1"

            //约束布局：https://developer.android.com/jetpack/compose/layouts/constraintlayout?hl=zh_cn
            const val constraintlayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0"
            //分页：https://developer.android.com/jetpack/androidx/releases/paging?hl=zh-cn
            const val pagingCompose = "androidx.paging:paging-compose:1.0.0-alpha14"
        }

        object Hilt{
            //注入：https://developer.android.com/jetpack/androidx/releases/hilt?hl=zh-cn#hilt-navigation-compose_version_100_2
            const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
            //Hilt注入:https://developer.android.com/training/dependency-injection/hilt-android?hl=zh-cn
            const val hiltAndroid = "com.google.dagger:hilt-android:2.45"

            const val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.45"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"

            //https://developer.android.com/jetpack/androidx/releases/lifecycle?hl=zh-cn
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        }


        object Compose {
            const val version = "1.3.2"

            const val composeUI = "androidx.compose.ui:ui:$version"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"


            const val material = "androidx.compose.material:material:1.1.1"



        }

    }

    object Google{

        //GSON:https://github.com/google/gson
        const val Gson = "com.google.code.gson:gson:2.8.9"

        //官方组件库
        //accompanist  https://github.com/google/accompanist
        object Accompanist{
            const val accompanistVersion  = "0.28.0"


            //https://github.com/google/accompanist/tree/main/insets
            const val accompanistInsets = "com.google.accompanist:accompanist-insets:$accompanistVersion"

            const val accompanistInsetsUI = "com.google.accompanist:accompanist-insets-ui:$accompanistVersion"

            const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"

            //viewpager
            const val pager = "com.google.accompanist:accompanist-pager:$accompanistVersion"

            //导航
            const val navigationmaterial = "com.google.accompanist:accompanist-navigation-material:$accompanistVersion"
            //带动画的导航
            const val navigationanimation = "com.google.accompanist:accompanist-navigation-animation:$accompanistVersion"
            //预加载效果
            const val placeholdermaterial = "com.google.accompanist:accompanist-placeholder-material:$accompanistVersion"

            const val placeholder = "com.google.accompanist:accompanist-placeholder:$accompanistVersion"

        }
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Squareup{

        // OkHttp 框架：https://github.com/square/okhttp
        const val okhttp3 = "com.squareup.okhttp3:okhttp:4.10.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"

        //retrofit :https://github.com/square/retrofit
        const val retrofit2 = "com.squareup.retrofit2:retrofit:2.9.0"
        const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"

    }

    object Other{
        //log : https://github.com/ihsanbal/LoggingInterceptor
        const val loggingInterceptor = "com.github.ihsanbal:LoggingInterceptor:3.1.0"

        //下拉刷新：https://github.com/RicardoJiang/compose-refreshlayout
        const val composeRefreshlayout = "io.github.shenzhen2017:compose-refreshlayout:1.0.0"

        //图片加载：https://github.com/coil-kt/coil
        const val composeCoil = "io.coil-kt:coil-compose:1.4.0"

        //日志调试框架：https://github.com/getActivity/Logcat
        const val getActivityLogcat= "com.github.getActivity:Logcat:10.8"

    }

}