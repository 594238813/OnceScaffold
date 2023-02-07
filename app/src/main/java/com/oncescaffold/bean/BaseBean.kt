package com.oncescaffold.bean

import java.io.Serializable

data class BaseBean<T>(
        val code:String?,
        val returnCode:String?,
        val message:String?,
        val msg:String?,
        val data:T?
) : Serializable