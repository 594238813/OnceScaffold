package com.oncescaffold.ui.page.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oncescaffold.R
import com.oncescaffold.ui.page.home.HomePage
import com.oncescaffold.ui.theme.LocalGlobalNavController
import com.oncescaffold.ui.theme.OnceTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import com.oncescaffold.ui.page.me.UserPage
import com.oncescaffold.ui.page.news.NewsPage
import com.oncescaffold.ui.page.routeplan.RoutePlanPage
import com.oncescaffold.widgets.BottomNavigationItemNoIndication

@Preview
@Composable
fun MainPage(){

    val navController = LocalGlobalNavController.current

    //用于 Scaffold 内部的 导航
    val navCtrl = rememberNavController()
    //material包 的 Scaffold
    Scaffold(
        bottomBar = {
            //底部导航按钮
            ScaffoldButtonBar(navCtrl)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { } ,  backgroundColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp)
            ) {
                Image(painter = painterResource(id = R.mipmap.icon_main_qrcode),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigate("QRPage")
                        })
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = {
            //一定要是用 这个it 让内容 在ButtonBar上面，否则内部会显示不全
            NavHost(
                modifier = Modifier.background(Color.White).padding(it),
                navController = navCtrl,
                startDestination = HOME
            ) {
                composable(HOME){ HomePage() }
                composable(ROUTEPLAN){ RoutePlanPage() }
                composable(QRCODE){   }
                composable(NEWS){ NewsPage() }
                composable(USER){ UserPage() }
            }
        }
    )
}

//底部按钮
@Composable
private fun ScaffoldButtonBar(navCtrl: NavHostController) {

    val items = listOf(
        BottomNavBean.Home,
        BottomNavBean.Message,
        BottomNavBean.QRCode,
        BottomNavBean.Tags,
        BottomNavBean.User
    )

    var selectedItem by remember { mutableStateOf(0) }

    //使用WindowCompat.setDecorFitsSystemWindows(window, false)把内容延伸到 状态栏背后
    //底部导航也会被延伸
    //底部导航,  因为使用了沉浸 所以 组件会被3个按钮 挡住 所以 navigationBarsPadding 高出这个距离
    BottomNavigation(modifier = Modifier
        .navigationBarsPadding(),elevation = 0.dp) {
        val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEachIndexed { index, tab ->
            //BottomNavigationItem 组件默认有水波纹效果
            //copy 源码修改，1、移除水波纹  2、增加icon和label 的间距 3、移除label左右间距，可以容纳多几个字符
            BottomNavigationItemNoIndication(
                modifier = Modifier.background(Color.White),
                icon = {
                    NavIconView(items, index, selectedItem)
                },
                label = { Text(stringResource(id = tab.stringId), maxLines = 1) },
                selected = currentDestination?.hierarchy?.any { it.route == tab.routeName } == true,
                onClick = {

                    if (index == 2) {
                        return@BottomNavigationItemNoIndication
                    } else {
                        selectedItem = index
                        if (tab.routeName != currentDestination?.route) {
                            //https://www.pudn.com/news/634e3dfa2aaf6043c93ce705.html

                            //在navCtrl.navigate(..) 导航跳转时，会创建一个新的 NavBackStackEntry 对象， 压入回退栈
                            //点击系统back会弹出 Entry，返回前一个页面 ，返回后希望保持前一个页面的状态
                            //切换页面本质是在重组调用不同的 Composable


                            //NavBackStackEntry 和Fragment 责任一样
                            //NavBackStackEntry 本身就是 ViewModelStoreOwner

                            //直接使用navCtrl.navigate切换页面， 会导致 NavBackStack 无限增长，所以需要及时移除不需要的页面
                            //但主页的tab需要保存状态，所以ViewModel就不能销毁，存放在 NavControllerViewModel 的 map中
                            //以便下次恢复


                            //所以在 navCtrl.navigate(..) 跳转时 保存状态，避免同一个Entry入栈，如果之前保存了就恢复
                            //仅限于tab 导航
                            navCtrl.navigate(tab.routeName) {
                                popUpTo(navCtrl.graph.findStartDestination().id) {
                                    //出栈的 BackStack 保存状态
                                    saveState = true
                                }
                                // 避免点击同一个 Item 时反复入栈
                                launchSingleTop = true
                                // 如果之前出栈时保存状态了，那么重新入栈时恢复状态
                                restoreState = true
                            }
                        }
                    }

                },
                selectedContentColor = OnceTheme.colors.mainTabSelectedContentColor,
                unselectedContentColor = OnceTheme.colors.mainTabUnselectedContentColor
            )
        }
    }
}


@Composable
private fun NavIconView(
    list: List<BottomNavBean>,
    index: Int,
    selectedItem: Int,
){
    list[index].icon?.let {
        Icon(painterResource(id =
        if(index==selectedItem) list[index].icon!! else list[index].iconNormal!!
        ), contentDescription = null, Modifier.size(22.dp))
    }

}

sealed class BottomNavBean(
    var routeName: String,
    @StringRes var stringId: Int,
    @DrawableRes var icon: Int?,
    @DrawableRes var iconNormal: Int?
) {
    object Home: BottomNavBean(HOME, R.string.main_home, R.mipmap.icon_home,R.mipmap.icon_home_normal)
    object Message: BottomNavBean(ROUTEPLAN, R.string.main_message, R.mipmap.icon_routeplanning,R.mipmap.icon_routeplanning_normal)
    object QRCode: BottomNavBean(QRCODE, R.string.main_qrcode,null,null)
    object Tags: BottomNavBean(NEWS, R.string.main_tags, R.mipmap.icon_goodgoods,R.mipmap.icon_goodgoods_normal)
    object User: BottomNavBean(USER, R.string.main_user, R.mipmap.icon_me,R.mipmap.icon_me_normal)
}

const val HOME = "home"
const val ROUTEPLAN  = "RoutePlan"
const val QRCODE = "qrcode"
const val NEWS  = "News"
const val USER = "user"



