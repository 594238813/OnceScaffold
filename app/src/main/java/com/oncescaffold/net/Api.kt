package com.oncescaffold.net

import com.oncescaffold.bean.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api{

    //登录
    @POST("")
    suspend fun loginName(@Body registerReqBean: RegisterReqBean): BaseBean<TokenResBean>


}