package com.oncescaffold.ui.page.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oncescaffold.R
import com.oncescaffold.ui.theme.color_f7f7f7

@Composable
fun NewsPage(){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color_f7f7f7)){
        NewsBody()
    }
}

@Composable
fun NewsBody(){
//    带  by的 函数 ，无法穿透函数  做监听
    var flag = remember{ mutableStateOf(false) }
    Column(modifier = Modifier.statusBarsPadding()) {

        Button(onClick = {
            flag.value = !flag.value
        }){
            Text("弹窗")
        }
    }
    NewsDialog(flag)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewsDialog(flag: MutableState<Boolean>) {
    if(flag.value){
        Dialog(
            onDismissRequest = { flag.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
//                usePlatformDefaultWidth = false
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(),
                //在屏幕的什么位置
                contentAlignment = Alignment.Center){
                Card(shape = RoundedCornerShape(8.dp),
                    elevation = 0.dp,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.Transparent
                ) {
                    Column() {
                        Box(modifier = Modifier.fillMaxWidth()){
                            Image(painterResource(id = R.mipmap.pic_newversion_bg),contentDescription = null)
                        }
                        Column(modifier =Modifier.background(Color.White)){
                            Text("这里是内容")

                            Button(modifier = Modifier.fillMaxWidth()
                                .padding(start=10.dp,end=10.dp),
                                onClick = {
                                    flag.value = false
                                }) {
                                Text("确定")
                            }
                        }

                    }
                }
            }
        }



    }
}