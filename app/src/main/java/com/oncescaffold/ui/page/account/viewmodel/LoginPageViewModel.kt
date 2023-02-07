package com.oncescaffold.ui.page.account.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oncescaffold.bean.RegisterReqBean
import com.oncescaffold.net.RetrofitClient.retrofitDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject


@HiltViewModel
class LoginPageViewModel @Inject constructor(): ViewModel() {

    //https://juejin.cn/post/7048980213811642382
    //MVI架构强调对  单向数据流动 + 状态集中管理
    //MVVM 是多个数据流， 一个State对应一个LiveData

    //单向数据流：UI只能通过loginPageState修改状态
    //         通过 uiStateShared 对象 改变UI的状态
    // 数据一致性，唯一可信源

    //UI State UI的状态，UI状态改变都是通过这个 loginPageState 进行改变，且LoginViewState()对象是不变的
    var loginPageState by mutableStateOf(LoginViewState())

    //给View订阅的状态
    val uiState = MutableSharedFlow<LoginViewEvent>()
    val uiStateShared = uiState.asSharedFlow()


    //UI通过这个方法 改变状态
    fun dispatch(action: LoginViewAction) {
        when (action) {
            is LoginViewAction.Login -> login()
            is LoginViewAction.ClearPhoneNumber -> clearPhoneNumber()
            is LoginViewAction.UpdatePhoneNumber -> phoneNumberChar(action.phoneNumber)
            is LoginViewAction.UpdatePassword -> updatePassword(action.password)
            is LoginViewAction.IsCheckPrivacy -> isCheckPrivacy(action.boolean)
        }
    }

    private fun isCheckPrivacy(boolean: Boolean) {
        loginPageState = loginPageState.copy(isCheckPrivacy = boolean)
    }

    private fun updatePassword(password: String) {
        loginPageState = loginPageState.copy(password = password)
    }

    private fun phoneNumberChar(phoneNumber: String) {
        loginPageState = loginPageState.copy(phoneNumber = phoneNumber)
    }

    private fun clearPhoneNumber() {
        loginPageState = loginPageState.copy(phoneNumber = "")
    }

    //登录
    fun login() {
        flow{
            if (!loginPageState.isCheckPrivacy) {
                return@flow uiState.emit(LoginViewEvent.ErrorMessage("请选勾选协议"))
            }

            if(loginPageState.phoneNumber.isEmpty() or loginPageState.password.isEmpty()){
                return@flow uiState.emit(LoginViewEvent.ErrorMessage("手机号或密码为空"))
            }

            val loginReqBean  = RegisterReqBean(
                loginName = loginPageState.phoneNumber ,
                password = loginPageState.password,
                code = "",
                inviterUserId = "",
                deviceId = UUID.randomUUID().toString(),
                registerType = "1",
                thirdId = "",
                mobile = "",
            )
            //loginName 可以封装一个解析
//            val result = retrofitDefault.loginName(loginReqBean)
//            Log.e("flow","${result.code}")

            uiState.emit(LoginViewEvent.ErrorMessage("登录失败"))

            emit("")
        }.onStart {
            Log.e("flow","onStart")
        }.onCompletion {
            Log.e("flow","onCompletion")
        }.catch {
            Log.e("flow","catch")
            it.printStackTrace()
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }



    //封装 可以放到BaseViewModel
    fun baseRequest(block :suspend()-> Unit): Job {
        return flow {
            block()
            emit("")
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun login2(){
        baseRequest{
            val loginReqBean  = RegisterReqBean(
                loginName = loginPageState.phoneNumber ,
                password = loginPageState.password,
                code = "",
                inviterUserId = "",
                deviceId = "",
                registerType = "1",
                thirdId = "",
                mobile = "",
            )
            val result = retrofitDefault.loginName(loginReqBean)
        }

    }




}


/**
 * 表示页面的状态
 */
data class LoginViewState(
    val phoneNumber: String = "",
    val password: String = "",
    val isCheckPrivacy:Boolean = false,
)

/**
 * https://juejin.cn/post/7022624191723601928
 * M:MVI的Model主要指UI状态（State）
 * 可能的问题:UI的状态是相互独立的，建议最好不同部分的状态 分开
 * UiState diffing：解决连续发出的数据流是否相同,在订阅的时候，LiveData 或Flow使用 distinctUntilChanged() 防抖
 */
sealed class LoginViewEvent {
    object PopBack : LoginViewEvent()
    data class ErrorMessage(val message: String) : LoginViewEvent()
}

/**
 * I:MVI的I  不是Activity的 Intent , 是用户的任何操作都被包装成Intent(意图)后发送给Model层进行数据请求
 */
sealed class LoginViewAction {
    object Login : LoginViewAction()
    object ClearPhoneNumber : LoginViewAction()
    data class UpdatePhoneNumber(val phoneNumber: String) : LoginViewAction()
    data class UpdatePassword(val password: String) : LoginViewAction()
    data class IsCheckPrivacy(val boolean: Boolean) : LoginViewAction()
}