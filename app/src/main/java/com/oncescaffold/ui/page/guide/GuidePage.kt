package com.oncescaffold.ui.page.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.oncescaffold.ui.theme.LocalGlobalNavController
import com.oncescaffold.ui.theme.OnceTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GuidePage(){
    var flag by remember{ mutableStateOf(false) }
    val navController = LocalGlobalNavController.current

    if(flag) {
        val lists = mutableListOf(Color.Green, Color.White, Color.White, Color.White)
        HorizontalPager(
            count = lists.size,
            modifier = Modifier.background(OnceTheme.colors.background),
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        ) { page ->
            Box(
                Modifier
                    .fillMaxSize()
                    .background(lists[page]), contentAlignment = Alignment.Center
            ) {
                //最后一张图 显示按钮 进入主界面
                if (page == lists.size - 1) {
                    Button(
                        onClick = {
                            navController.popBackStack()
                            navController.navigate("MainPage")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = OnceTheme.colors.primaryBtnBg
                        )
                    ) {
                        Text(
                            "进入app", color = OnceTheme.colors.primaryBtnTxt
                        )
                    }
                } else {
                    Text("向左滑动 下一个引导页")
                }
            }
        }
    }
    //fake Preload
    LaunchedEffect(Unit){
        flag = true
    }

}
