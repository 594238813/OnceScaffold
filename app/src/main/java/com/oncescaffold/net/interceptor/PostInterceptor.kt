package com.oncescaffold.net.interceptor

import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import java.util.HashMap

/**
 *  author : hyt
 *  date : 2021/3/13
 *  description :
 */
class PostInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val requestBody = request.body

        val newRequest: Request
        val newBody :RequestBody
        val mGson = Gson()

        //在拦截器中修改请求数据, 比如加签、加密等等
        //POST 请求
        //在Retrofit中定义接口，接口中的请求的参数
        //          如果请求均为String，或者 是bean中 都是string，在拦截器中获取Body
        //              通过循环获取 key 和value ， for -> requestBody.encodedName(i)
        //          只要不是上面的情况 ，获取 request 中的body 通过Buffer读取
        //                            val buffer = Buffer()
        //                            requestBody.writeTo(buffer)
        //                            val oldParamsJson = buffer.readUtf8()
        //          否则会报错


        if(requestBody is FormBody){
            val mapParams: MutableMap<String?, String?> = HashMap()
            for (i in 0 until requestBody.size) {
                mapParams[requestBody.encodedName(i)] = requestBody.encodedValue(i)
            }
            val json = mGson.toJson(mapParams)

            newBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        }else{
            val buffer = Buffer()
            requestBody?.writeTo(buffer)
            val json = buffer.readUtf8()

            newBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        }
        newRequest = request.newBuilder().run {
            this.url(request.url)

            //必须先移除，Header里面是个数组
            this.removeHeader("sign")

            this.addHeader("sign", "sign")
            this.post(newBody)
            this.build()
        }


        return chain.proceed(newRequest)
    }
}