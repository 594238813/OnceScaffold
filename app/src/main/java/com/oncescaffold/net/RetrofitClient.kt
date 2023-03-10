package com.oncescaffold.net

import com.google.gson.Gson
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.oncescaffold.NGURL_BASE
import com.oncescaffold.URL_BASE
import com.oncescaffold.URL_wanandroid
import com.oncescaffold.net.interceptor.PostInterceptor
import com.oncescaffold.net.interceptor.RefreshTokenInterceptor
import com.oncescaffold.net.wanandroid.WanAndroidApi
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Inject


object RetrofitClient{

    //默认后台地址
    val defaultOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(RefreshTokenInterceptor())      //刷新token的拦截器 必须放第一个
        .addInterceptor(PostInterceptor())
        .addInterceptor(
            LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(Platform.WARN)
                .build())
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .dns(ApiDns())
        .proxy(Proxy.NO_PROXY)
        .build()

    val retrofitDefault: Api = Retrofit.Builder()
        .client(defaultOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .baseUrl(NGURL_BASE)
        .build().create(Api::class.java)


//建议 最好是 一个后台地址 对应一个retrofit对象，对应一个okhttp对象,第三方的也要分开
//合并在一起，拦截器的判断会非常麻烦
//最好图片上传的url也分开


    //wanandroid
    val wanandroidOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(Platform.WARN)
                .build())
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
//        .dns(ApiDns())
        .proxy(Proxy.NO_PROXY)
        .build()


    //wanandroid 接口
    val retrofitWanAndroid: WanAndroidApi = Retrofit.Builder()
        .client(wanandroidOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .baseUrl(URL_wanandroid)
        .build().create(WanAndroidApi::class.java)


}