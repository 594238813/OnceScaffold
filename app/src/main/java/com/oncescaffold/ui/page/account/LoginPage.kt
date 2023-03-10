package com.oncescaffold.ui.page.account

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.oncescaffold.R
import com.oncescaffold.phoneRules
import com.oncescaffold.ui.page.account.viewmodel.LoginPageViewModel
import com.oncescaffold.ui.page.account.viewmodel.LoginViewAction
import com.oncescaffold.ui.page.account.viewmodel.LoginViewEvent
import com.oncescaffold.ui.theme.*

@Preview
@Composable
fun LoginPage() {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()

        .background(colorResource(id = android.R.color.white))){
        Column(modifier = Modifier.verticalScroll(scrollState))  {
            Login_Logo()
            Spacer(modifier = Modifier.height(80.dp))
            Login_Content()
        }
    }
}

@Preview
@Composable
private fun Login_Content(){

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 24.dp, end = 24.dp),
    ){

        Login_enterInfo()
        //协议
        Login_agreement()
        Spacer(modifier = Modifier.height(24.dp))
        //按钮
        Login_SubmitBtn()
        Spacer(modifier = Modifier.height(100.dp))
        //第三方登录
        Login_thLogin()
        Spacer(modifier = Modifier.height(100.dp))
    }
}


@Preview
@Composable
private fun Login_Logo(){
    Spacer(modifier = Modifier.height(96.dp))
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
        Image(modifier = Modifier.size(80.dp),
            painter =  rememberImagePainter(
                ContextCompat.getDrawable(LocalContext.current,R.mipmap.ic_launcher))
            ,
            contentDescription = null)
    }
}

@Composable
fun Login_enterInfo(loginPageViewModel: LoginPageViewModel = hiltViewModel()) {
    val loginPageState = loginPageViewModel.loginPageState
    BasicTextField(
        value = loginPageState.phoneNumber,
        onValueChange = {

            val temp = it.trim().filter { txt->
                txt in phoneRules
//                txt.isDigit()
            }.take(11)

            loginPageViewModel.dispatch(LoginViewAction.UpdatePhoneNumber(temp))
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        textStyle = TextStyle(color = color_1f3456, fontSize = 15.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
        decorationBox = { innerTextField->
            Column(verticalArrangement = Arrangement.Center){
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier ){
                    Box(modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        if(loginPageState.phoneNumber.isEmpty()){
                            Text(text = "请输入手机号",
                                color = color_c8c9cc,
                                style = TextStyle(fontSize = 15.sp))
                        }
                        innerTextField()
                    }

                    if(loginPageState.phoneNumber.isNotEmpty()){
                        Image(painterResource(id = R.mipmap.icon_cleartxt),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                loginPageViewModel.dispatch(LoginViewAction.ClearPhoneNumber)
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color_ebebeb)
                )
            }
        },
        cursorBrush = SolidColor(color_ff8853)
    )

    var passwordHidden by remember{ mutableStateOf(true)}
    BasicTextField(
        value = loginPageState.password,
        onValueChange = {
            val temp = it.trim().take(20)
            loginPageViewModel.dispatch(LoginViewAction.UpdatePassword(temp))
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        textStyle = TextStyle(color = color_1f3456, fontSize = 15.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        decorationBox = { innerTextField->
            Column(verticalArrangement = Arrangement.Center){
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier ){

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        if(loginPageState.password.isEmpty()){
                            Text(text = "请输入密码（6-20位数字+字母）",
                                color = color_c8c9cc,
                                style = TextStyle(fontSize = 15.sp))
                        }
                        innerTextField()
                    }

                    Image(
                        painterResource(id = if(passwordHidden) R.mipmap.icon_ciphertext else R.mipmap.icon_plaintext),
                        contentDescription = null,
                        modifier = Modifier.clickable { passwordHidden = !passwordHidden },
                    )
                }
                Spacer(modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color_ebebeb)
                )
            }
        },
        cursorBrush = SolidColor(color_ff8853),
        visualTransformation = if(passwordHidden) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun Login_thLogin() {

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("其他方式登录",
            modifier = Modifier.fillMaxWidth(),
            color = color_747fa0,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center){

            Image(modifier = Modifier
                .size(48.dp)
                .weight(1f),
                painter = painterResource(id = R.mipmap.icon_qq ),
                contentDescription = null)
            Image(modifier = Modifier
                .size(48.dp)
                .weight(1f),
                painter = painterResource(id = R.mipmap.icon_wechat ),
                contentDescription = null)
            Image(modifier = Modifier
                .size(48.dp)
                .weight(1f),
                painter = painterResource(id = R.mipmap.icon_alipay ),
                contentDescription = null)
            Image(modifier = Modifier
                .size(48.dp)
                .weight(1f),
                painter = painterResource(id = R.mipmap.icon_phone ),
                contentDescription = null)
        }

    }

}

@Composable
private fun Login_SubmitBtn(loginPageViewModel: LoginPageViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(Unit){
        loginPageViewModel.uiStateShared.collect{
            when(it){
                is LoginViewEvent.ErrorMessage->{
                    Toast.makeText(context ,it.message,Toast.LENGTH_LONG).show()
                }
                is LoginViewEvent.PopBack->{

                }
            }
        }
    }


    Button(modifier = Modifier.fillMaxWidth().height(44.dp),
        onClick = {
            //登录
            loginPageViewModel.login()

        }, shape = RoundedCornerShape(8),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color_ff8853
        )){
        Text("登录", color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.height(20.dp))
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween){
        Text("注册", color = color_747fa0,
            fontSize = 14.sp,
        )

        Text("忘记密码？", color = color_747fa0,
            fontSize = 14.sp,
        )
    }

}

@Preview
@Composable
private fun Login_agreement(loginPageViewModel: LoginPageViewModel = hiltViewModel()){
    val agreementText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color_747fa0)) {
            append("已阅读并同意")
        }
    }+buildAnnotatedString{
        pushStringAnnotation(tag = "yinsi", annotation = "aaa")
        withStyle(style = SpanStyle(color = color_206af5)){
            append("《隐私权政策》")
        }
        pop()
    }+ buildAnnotatedString {
        pushStringAnnotation(tag = "agreement", annotation = "bbb")
        withStyle(style = SpanStyle(color = color_206af5)){
            append("《用户协议》")
        }
        pop()
    }


    Row{
        Box{
            Image(painterResource(id =
            if(loginPageViewModel.loginPageState.isCheckPrivacy)
                R.mipmap.icon_privacy
            else
                R.mipmap.icon_privacy_normal
            ),
                contentDescription = null,
                modifier = Modifier.clickable {
                    //勾选协议
                    loginPageViewModel.dispatch(LoginViewAction.IsCheckPrivacy(
                        !loginPageViewModel.loginPageState.isCheckPrivacy
                    ))
                },
            )
        }
        ClickableText(
            text = agreementText,
            onClick ={ offset->
                agreementText.getStringAnnotations(
                    tag = "yinsi", start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    Log.e("tag", "你已经点到 ${annotation.item} 啦")
                }
                agreementText.getStringAnnotations(
                    tag = "agreement", start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    Log.e("tag", "你已经点到 ${annotation.item} 啦")
                }
            },
            modifier = Modifier.padding(start = 8.dp),
        )

    }
}