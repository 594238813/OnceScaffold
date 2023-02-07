package com.oncescaffold.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val color_333333 = Color(0xFF333333)
val color_fff = Color(0xffffffff)
val color_da3824 = Color(0xffDA3824)
val color_f7f7f7 = Color(0xFFF7F7F7)
val color_999999 = Color(0xFF999999)
val color_ff8853 = Color(0xFFFF8853)
val color_8e94b0 = Color(0xFF8E94B0)
val color_1f3456 = Color(0xFF1F3456)
val color_eeeeee = Color(0xFFEEEEEE)
val color_c8c9cc = Color(0xFFC8C9CC)
val color_ebebeb = Color(0xFFEBEBEB)

val color_747fa0 = Color(0xFF747FA0)
val color_206af5 = Color(0xFF206AF5)






//新建一个稳定类，定义有，背景、主要文本、次要文本等 的颜色
//如果 效果图颜色相近 ， 可以在这里增加，尽量在 Theme 初始化颜色
@Stable
class AppColors(
    background: Color = Color.White,
    textPrimary: Color,
    textSecondary: Color,
    primaryBtnTxt:Color,
    secondaryBtnTxt:Color,
    primaryBtnBg: Color,
    secondaryBtnBg: Color,
    mainTabSelectedContentColor:Color,
    mainTabUnselectedContentColor:Color,
) {
    var background: Color by mutableStateOf(background)
        private set
    var textPrimary: Color by mutableStateOf(textPrimary)
        internal set
    var textSecondary: Color by mutableStateOf(textSecondary)
        private set
    var primaryBtnBg: Color by mutableStateOf(primaryBtnBg)
        internal set
    var secondaryBtnBg: Color by mutableStateOf(secondaryBtnBg)
        private set
    var primaryBtnTxt: Color by mutableStateOf(primaryBtnTxt)
        private set
    var secondaryBtnTxt: Color by mutableStateOf(secondaryBtnTxt)
        private set
    var mainTabSelectedContentColor: Color by mutableStateOf(mainTabSelectedContentColor)
        private set
    var mainTabUnselectedContentColor: Color by mutableStateOf(mainTabUnselectedContentColor)
        private set
}