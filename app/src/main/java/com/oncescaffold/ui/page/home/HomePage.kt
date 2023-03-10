package com.oncescaffold.ui.page.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.oncescaffold.bean.ArticleBean
import com.oncescaffold.ui.page.home.viewmodel.HomePageViewModel
import com.oncescaffold.widgets.PullRefreshLayout
import com.zj.refreshlayout.SwipeRefreshLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    homePageViewModel: HomePageViewModel = hiltViewModel()
) {
    //此处不能使用 viewmodel()  会出现  has no zero argument constructor
    //谷歌说  这个功能被迁移到  Hilt  里面
    //https://issuetracker.google.com/issues/181890304

    val state = remember {
        homePageViewModel.homePageViewModelState
    }
    val articleList = state.pagingData.collectAsLazyPagingItems()


    //LazyListState 可以放到 ViewModel中
    val listState = if (articleList.itemCount > 0) state.listState else LazyListState()


    DisposableEffect(Unit) {
        Log.i("debug", "onStart")
        onDispose {
            Log.i("debug", "onDispose")
        }
    }

    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            refreshing = false
            listState.reenableScrolling(scope)
        }
    }
    SwipeRefreshLayout(isRefreshing = refreshing, onRefresh = {
        refreshing = true
        listState.disableScrolling(scope)
    }) {
        LazyColumn(state = listState,modifier= Modifier.fillMaxSize()) {
            item(1){
                TopBanner(homePageViewModel)
            }
            itemsIndexed(items = articleList){ index, item->
                if (item != null) {
                    ArticleLayout(item)
                }
            }
        }
    }

}


@Composable
fun ArticleLayout(it: ArticleBean) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)) {
        Box {
            Text(
                it.title,
                color = Color.Black,fontSize = 16.sp,maxLines = 1,overflow = TextOverflow.Ellipsis)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TopBanner(homePageViewModel: HomePageViewModel,
) {
    val bannerList by homePageViewModel.getBanner().collectAsState(null)
    val pagerState = rememberPagerState()
    HorizontalPager(state = pagerState,count = bannerList?.data?.size?:0, modifier = Modifier
        .aspectRatio(900 / 500f)
    ) { page->

        val item = bannerList?.data?.get(page)
        Image(painter = rememberImagePainter(data = item?.imagePath),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "",
            contentScale = ContentScale.Fit)
    }

}

fun LazyListState.disableScrolling(scope: CoroutineScope) {
    scope.launch {
        scroll(scrollPriority = MutatePriority.PreventUserInput) {
            // Await indefinitely, blocking scrolls
            awaitCancellation()
        }
    }
}

fun LazyListState.reenableScrolling(scope: CoroutineScope) {
    scope.launch {
        scroll(scrollPriority = MutatePriority.PreventUserInput) {
            // Do nothing, just cancel the previous indefinite "scroll"
        }
    }
}