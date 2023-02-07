package com.oncescaffold.net.interceptor

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.oncescaffold.bean.BaseBean
import com.oncescaffold.net.RetrofitClient.defaultOkHttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.atomic.AtomicBoolean

class RefreshTokenInterceptor : Interceptor {

    private val mIsTokenExpired: AtomicBoolean = AtomicBoolean(false)

    override fun intercept(chain: Interceptor.Chain): Response {
        //直接请求
        val response:Response = chain.proceed(chain.request())

        val body: ResponseBody? = response.body

        val buffer =  body?.source()?.buffer?.clone()

        val charset: Charset = body?.contentType()?.charset(StandardCharsets.UTF_8)
            ?: StandardCharsets.UTF_8

        val bean = Gson().fromJson<BaseBean<Any>>(
            buffer?.readString(charset),
            object : TypeToken<BaseBean<Any>>() {}.type
        )
        //判断返回 401   http 状态 的 过期  或者 其他code 表示过期
        if( response.code == HTTP_UNAUTHORIZED  || (bean!=null && bean.code!=null && bean.code =="1020")){
            getNewToken()
            return chain.proceed( response.request.newBuilder().build()  )
        }

        Log.e("ref","RefreshTokenInterceptor")

        return response
    }

    //用RefreshToken获取新token
    private fun getNewToken(){

        synchronized(this) {
            //防止 刷新失败 让其他线程继续刷新   CAS 操作  false 是否可以转为 true
            if(mIsTokenExpired.compareAndSet(mIsTokenExpired.get(),true)){
                try {
                    Log.e("auth", "获取token")
                    val request = Request.Builder().url(
                        """
                            这里是刷新token的接口
                    """.trimIndent()
                    ).build()
                    val call = defaultOkHttpClient.newCall(request)
                    val response: Response = call.execute()

                    if (response.code == 200) {
                        //解析token
                        val body = response.body
                        val bodyString = body?.string()
                        Log.e("auth", "$bodyString")
                        if (bodyString != null) {

                            //解析json GSON.fromJson会抛出 JsonIOException, JsonSyntaxException异常
                            //GSON 异常也跳转登录
//                            val bean = Gson().fromJson<Bean>(
//                                bodyString.reader(),
//                                object : TypeToken<Bean>() {}.type
//                            )

                            //解析 通过GSON解析 ， 然后替换token
                            Log.e("auth", "替换token")


                            //替换token后 ，应该在某个时间 把 mIsTokenExpired 改成false,
                            //因为token可能再次过期，极端情况下，App一直在前台，导致if判断的CAS 一直为false
                            nextRefreshTokenJob()

                        } else {
                            //请求返回错误
                            Log.e("auth", "刷新token异常 跳转登录")
                            //取消所有请求
                            //跳转登录
                            cancleJump()
                        }
                    } else {
                        //http请求返回错误
                        Log.e("auth", "刷新token异常 跳转登录")
                        //取消所有请求
                        //跳转登录
                        cancleJump()
                    }
                }catch (ex : JsonIOException){
                    //GSON解析异常
                    cancleJump()
                }catch (ex: JsonSyntaxException){
                    //GSON解析异常
                    cancleJump()
                } catch (ex: Exception) {
                    //其他异常，防止 刷新失败 让其他线程继续刷新
                    mIsTokenExpired.set(false)
                    ex.printStackTrace()
                }
            }else{
                //第一个线程请求成功
                //第二个线程会到这里 ，不需要考虑false的情况，
                //后面会 重新请求一次接口
            }
        }
    }

    private fun cancleJump(){
        cancleOkhttpRequest()
        jumpMainActivity()
    }

    //取消所有请求
    private fun cancleOkhttpRequest(){
        //发生错误 ，取消 正在执行的 和 在排队的
        defaultOkHttpClient.dispatcher.runningCalls().forEach {
            it.cancel()
        }
        defaultOkHttpClient.dispatcher.queuedCalls().forEach {
            it.cancel()
        }
    }

    //跳转主界面 弹窗
    private fun jumpMainActivity(){

    }

    //token 再次过期
    private fun nextRefreshTokenJob(){
        var interrupt = true
        GlobalScope.launch {
            while(interrupt){
                delay(5000)
                val runCount = defaultOkHttpClient.dispatcher.runningCallsCount()
                val queCount = defaultOkHttpClient.dispatcher.queuedCallsCount()
                if(runCount==0 && queCount==0){
                    interrupt = false
                    mIsTokenExpired.set(false)
                }
            }
        }
    }



}