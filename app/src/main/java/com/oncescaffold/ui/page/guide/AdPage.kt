package com.oncescaffold.ui.page.guide

import android.os.Process
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oncescaffold.R
import com.oncescaffold.ui.theme.LocalGlobalNavController
import com.oncescaffold.ui.theme.OnceTheme
import kotlinx.coroutines.delay

@Composable
fun AdPage(){
    var flag by remember{ mutableStateOf(false) }

    val navController = LocalGlobalNavController.current



    LaunchedEffect(Unit){
        //假装请求 判断是否有新协议
        delay(2000)
        flag = true

    }

    if(flag) {

        //隐私协议
        Dialog(
            onDismissRequest = { flag = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                Box(modifier = Modifier.fillMaxWidth().padding(15.dp).background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text("请仔细阅读",
                            color =  OnceTheme.colors.textPrimary,
                            fontSize =  OnceTheme.typography.h6.fontSize
                        )

                        Box(modifier= Modifier.height(280.dp)
                            .verticalScroll(rememberScrollState())){
                            Text(text = stringResource(id = R.string.policyTxt) ,
                                color = OnceTheme.colors.textPrimary,
                                fontSize = OnceTheme.typography.caption.fontSize)
                        }

                        Row {
                            Box(Modifier.weight(1f)) {
                                Button(onClick = {
                                    Process.killProcess(Process.myPid())
                                    flag = false
                                },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = OnceTheme.colors.secondaryBtnBg
                                    )
                                ) {
                                    Text("拒绝"
                                        , color = OnceTheme.colors.secondaryBtnTxt
                                    )
                                }
                            }

                            Spacer(Modifier.width(10.dp))

                            Box(Modifier.weight(1f)) {
                                Button(onClick = {
                                    flag = false
                                    //当前页面出栈
                                    navController.popBackStack()
                                    navController.navigate("GuidePage")
                                }, modifier =Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = OnceTheme.colors.primaryBtnBg)
                                ) {
                                    Text("同意", color = OnceTheme.colors.primaryBtnTxt)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}


