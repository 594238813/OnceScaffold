package com.oncescaffold.bean

data class HttpData<T>(
    val errorCode:Int?,
    val errorMsg:String?,
    val data:T?
)