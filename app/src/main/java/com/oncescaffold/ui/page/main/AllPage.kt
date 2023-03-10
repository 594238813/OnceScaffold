package com.oncescaffold.ui.page.main

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.oncescaffold.ui.page.account.LoginPage
import com.oncescaffold.ui.page.guide.AdPage
import com.oncescaffold.ui.page.guide.GuidePage
import com.oncescaffold.ui.page.qr.QRPage
import com.oncescaffold.ui.theme.LocalGlobalNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AndroidManifestPage(){
    val navController = LocalGlobalNavController.current
    AnimatedNavHost(navController,
        startDestination = "MainPage",
        enterTransition = {
            //自己进来
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
        },
        exitTransition ={
            //自己出去
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
        },
        popEnterTransition={
            //在栈里面   然后进来
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween( 300))
        },
        popExitTransition={
            //在栈里面   然后出去
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
        }
    ) {

        composable("AdPage"){
            //广告
            AdPage()
        }
        composable("GuidePage"){
            //4张图的引导
            GuidePage()
        }
        composable("MainPage"){
            //主界面
            MainPage()
        }
        composable("QRPage"){
            //二维码界面
            QRPage()
        }
        composable("LoginPage"){
            LoginPage()
        }
    }
}

