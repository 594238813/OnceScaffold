package com.oncescaffold.bean

import java.io.Serializable

/**
 *  author : hyt
 *  date : 2021/2/26
 *  description :
 */
data class TokenResBean(
        var userId :String?,
        var ngRefreshToken: String,
        var ngAccessToken: String,
        var thirdId:String
):Serializable
