package com.oncescaffold.ui.page.test

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LookaheadLayoutTest(){
    Column(Modifier.statusBarsPadding()) {
        var isTextHeight200DP by remember{ mutableStateOf(false) }
        var textHeightPx by remember{ mutableStateOf(0) }
        val textHeightPxAnim by animateIntAsState(textHeightPx)

        SimpleLookaheadLayout{
            Text("abc", Modifier
                .intermediateLayout { measurable, constraints, lookaheadSize ->
                    textHeightPx = lookaheadSize.height
                    val placeable = measurable.measure(
                        Constraints.fixed(lookaheadSize.width, textHeightPxAnim)
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }

                }
                .then(if (isTextHeight200DP) Modifier.height(200.dp) else Modifier)
                .clickable {
                    isTextHeight200DP = !isTextHeight200DP
                })
        }
        Text("message")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleLookaheadLayout(content: @Composable LookaheadLayoutScope.() -> Unit){
    LookaheadLayout( content){measurable,constraints->
        val placeable = measurable.map{
            it.measure(constraints)
            //it.measure(constraints)         //这里会报错 不允许 二次测量
        }
        val width = placeable.maxOf { it.width  }
        val height = placeable.maxOf{ it.height }
        layout(width,height){
            placeable.forEach { it.placeRelative(0,0) }
        }
    }
}