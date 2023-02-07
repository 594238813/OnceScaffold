package com.oncescaffold.ui.theme

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.navigation.NavHostController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.GlobalScope

//黑暗模式
//darkColors 和 lightColors 是  material 定义好的 颜色
private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)
//明亮模式
private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)
//以上不用


//像material 包里面代码一样 ，定义 app 自己的颜色
private val AppColorPalette = AppColors(
    background =  color_fff,
    textPrimary = color_333333,
    textSecondary = color_999999,
    primaryBtnBg =  color_ff8853,
    secondaryBtnBg = color_f7f7f7,
    primaryBtnTxt = color_fff,
    secondaryBtnTxt = color_999999,
    mainTabSelectedContentColor = color_ff8853,
    mainTabUnselectedContentColor = color_8e94b0,
)

//黑暗模式 本质也是样式
//private val AppDarkColorPalette = AppColors(
//  使用要把颜色填满
//)

//全局颜色
val LocalAppColors = compositionLocalOf  {
    AppColorPalette
}

//字体
val LocalAppTypography = compositionLocalOf {
    AppTypography
}

//全局导航
val LocalGlobalNavController = compositionLocalOf<NavHostController> {
    error("全局导航")
}
//SystemBarController
val LocalSystemBarController = compositionLocalOf<SystemUiController> {
    error("系统状态栏控制")
}

object OnceTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val typography: Typography
        @Composable
        get() = LocalAppTypography.current

}
//没有水波纹
object NoIndication : Indication {
    private object NoIndicationInstance : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        return NoIndicationInstance
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnceScaffoldTheme(darkTheme: Boolean = isSystemInDarkTheme(),
                      content: @Composable () -> Unit) {
    //颜色 跟随系统
    val appColors = if (darkTheme) {
        //暗黑 模式
        AppColorPalette
    } else {
        //明亮 模式
        AppColorPalette
    }

    //全局导航
    val navController = rememberAnimatedNavController()

    //使状态栏 导航栏 透明 这个
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, false) {
        //这里设置状态栏透明
        //如果 有列表需要 贴在导航栏后面 设置 setNavigationBarColor
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
        onDispose {}
    }

    //不使用MaterialTheme  且使用 material包下组件  依然可以使用MD样式
    //定义穿透
    CompositionLocalProvider(
        LocalAppColors provides appColors,                  //全局颜色
        LocalAppTypography provides AppTypography,          //全局文字样式
        LocalGlobalNavController provides navController,    //全局导航
        LocalSystemBarController provides systemUiController,//系统Bar控制器
        LocalIndication provides NoIndication,              //全局没有水波纹
        content = content
    )


}