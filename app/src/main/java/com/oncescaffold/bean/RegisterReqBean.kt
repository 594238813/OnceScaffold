package com.oncescaffold.bean

/**
 *  author : hyt
 *  date : 2021/3/13
 *  description :
 */
data class RegisterReqBean(
    var code: String,
    var deviceId: String,
    var inviterUserId: String,
    var loginName: String,
    var mobile: String,
    var password: String,
    var registerType: String,
    var thirdId: String,
)