package com.oncescaffold.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

import com.oncescaffold.ui.theme.color_da3824
import com.oncescaffold.ui.theme.color_ff8853
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun banner(){

    var executeChangePage by remember { mutableStateOf(false) }

    var currentPageIndex = 0

    val pageCount = 10
    val startIndex = (Int.MAX_VALUE / 2)
    val pagerState = rememberPagerState(initialPage = startIndex)

    val scope = rememberCoroutineScope()
//    with(pagerState) {
    LaunchedEffect(key1 = pagerState.currentPage,executeChangePage) {
        if (pagerState.pageCount > 0) {
            delay(3000)
            scope.launch {
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1).mod(pageCount))
            }
        }

    }
//    }

    Card(modifier = Modifier.padding(start = 12.dp, end = 12.dp).fillMaxWidth()
        .aspectRatio(351 / 80f),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp) {

        HorizontalPager(state = pagerState,count =  Int.MAX_VALUE,
            modifier = Modifier.pointerInput(pagerState.currentPage) {
                awaitPointerEventScope {
                    while (true) {
                        //PointerEventPass.Initial - 本控件优先处理手势，处理后再交给子组件
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        //获取到第一根按下的手指
                        val dragEvent = event.changes.firstOrNull()
                        when {
                            //当前移动手势是否已被消费
                            dragEvent!!.positionChangeConsumed() -> {
                                return@awaitPointerEventScope
                            }
                            //是否已经按下(忽略按下手势已消费标记)
                            dragEvent.changedToDownIgnoreConsumed() -> {
                                //记录下当前的页面索引值
                                currentPageIndex = pagerState.currentPage
                            }
                            //是否已经抬起(忽略按下手势已消费标记)
                            dragEvent.changedToUpIgnoreConsumed() -> {
                                //当页面没有任何滚动/动画的时候pagerState.targetPage为null，这个时候是单击事件
                                //当pageCount大于1，且手指抬起时如果页面没有改变，就手动触发动画
                                if (currentPageIndex == pagerState.currentPage && pagerState.pageCount > 1) {
                                    executeChangePage = !executeChangePage
                                }
                            }
                        }
                    }
                }
            }) { index->

            val page = (index - startIndex).floorMod(pageCount)
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    if (index % 2 == 0) {
                        color_ff8853
                    } else {
                        color_da3824
                    }
                ), contentAlignment = Alignment.Center){
                Text("${page}")
            }

        }
    }



}

//返回小于或等于代数商的最大(最接近正无穷大)整数值。
private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}