package com.oncescaffold.net.wanandroid

import com.oncescaffold.bean.ArticleBean
import com.oncescaffold.bean.BannerBean
import com.oncescaffold.bean.HttpData
import com.oncescaffold.bean.PageBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WanAndroidApi {

    @GET("banner/json")
    suspend fun getBanner(): HttpData<List<BannerBean>>


    @GET("article/list/{page}/json")
    suspend fun getArticle(@Path("page") page: Int, @Query("pageSize") rows: Int): HttpData<PageBean<ArticleBean>>
}