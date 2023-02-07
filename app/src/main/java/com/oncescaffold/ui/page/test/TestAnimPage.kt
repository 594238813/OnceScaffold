package com.oncescaffold.ui.page.test

import android.graphics.Point
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@Preview
@Composable
fun UserPage1(){
    var big by remember{ mutableStateOf(true) }

    val size by animateDpAsState(targetValue = if(big) 96.dp else 48.dp)
    val size1 = remember(big){ if(big) 96.dp else 48.dp  }

    var isR by remember{ mutableStateOf(true) }

    val bgColor by animateColorAsState(targetValue = if(isR) Color.Red else Color.Green )
    val anim = remember {
        Animatable(size1,Dp.VectorConverter)
    }
    val slowColor = remember {
        Animatable(bgColor)
    }

    LaunchedEffect(key1 = big){
//        anim.snapTo(if(big) 200.dp else 0.dp)      //瞬间完成
//        anim.animateTo(size1,TweenSpec(easing = FastOutLinearInEasing))   //动画完成
//        slowColor.animateTo(bgColor, SnapSpec(2000))   //和 snapTo 直接完成 没有动画效果
//        slowColor.animateTo(bgColor, snap(2000))   //
//        slowColor.animateTo(bgColor,TweenSpec(durationMillis = 9000,
//            delay = 5000,
//            easing = LinearOutSlowInEasing))

        //多段的 帧动画
//        anim.animateTo(size1, keyframes {
//            //动画时长
//            durationMillis = 450
//            //延迟
//            delayMillis = 100
//
//            //设置  初始值 就有 速度曲线
//            48.dp at 0 with FastOutLinearInEasing
//            //150ms的时候 变量是 144dp
//            //用with 添加速度曲线 150ms 到300ms 后一段
//            144.dp at 150 with FastOutLinearInEasing
//
//            //如果不写 默认LinearEasing
//            20.dp at 300
//        })


        //animateTo  第三个参数  初始速度
//        anim.animateTo(size1, spring(0.1f,Spring.StiffnessLow) ,2000.dp)

        //repeatable
        //第一个参数 重复次数
        //第二个参数 重复的 动画 , 根据类结构 不能填Spring(), 和 repeatable() 静止套娃
        //第三个参数 重复模式 ，重启、还是 倒放
        //当重复次数是 双数  同时还是倒放， 就会出问题
        //重复 不可靠
        //第四个参数 初始的时间启动偏移,
        // 分为延时性偏移、和  StartOffsetType.Delay  仅仅延迟
        //   快进偏移 StartOffsetType.FastForward   快进跳到 固定的offsetMillis 开始执行
//        anim.animateTo(size1, repeatable(3, tween(),RepeatMode.Reverse
//        , StartOffset(500, StartOffsetType.Delay)
//        ))

        //无限次数的 循环动画
        //如何取消  结束协程  用boolean 控制 协程
        anim.animateTo(size1, infiniteRepeatable(tween(),RepeatMode.Reverse
            , StartOffset(500, StartOffsetType.Delay)
        ))

    }

    Box(modifier = Modifier
        .size(anim.value)
        .background(Color.Green)
        .clickable {
            big = !big
            isR = !isR
        }){

    }
}

@Preview
@Composable
fun UserPage2(){

//    var size by remember{ mutableStateOf(48.dp) }

    var big by remember{ mutableStateOf(true) }
//    val size by animateDpAsState(targetValue = if(big) 48.dp else 96.dp)

    val size1 = remember(big) {
        if(big) 48.dp else 196.dp
    }

    val offset = remember(big) {
        if(big) 0.dp else 196.dp
    }
    val offsetAnim = remember{
        Animatable(offset, Dp.VectorConverter)
    }
    val anim = remember {
        Animatable(size1, Dp.VectorConverter)
    }
    LaunchedEffect(key1 = big){
//        anim.snapTo(if(big) 200.dp else 0.dp)      //瞬间完成
//        anim.animateTo(size1,TweenSpec(easing = FastOutLinearInEasing))   //动画完成
//        offsetAnim.animateTo(offset,TweenSpec(easing = LinearOutSlowInEasing))
//        offsetAnim.animateTo(offset, tween())
//        offsetAnim.animateTo(offset, snap())  //突变的


    }

    Box(modifier = Modifier.fillMaxSize() ){
        Box(modifier = Modifier
//            .offset(offsetAnim.value, offsetAnim.value)
            .size(size1)
            .background(Color.Green)
            .clickable {
                big = !big
            }){
        }
    }


}



@Preview
@Composable
fun UserPage333(){

    var isRun by remember{ mutableStateOf(false) }

    var big by remember { mutableStateOf(true) }

//    val bgColor by animateColorAsState(targetValue = if(big) Color.Red else Color.Blue )
    val bgColor = remember(big) { if(big) Color.Red else Color.Green }
    val colorAnim = remember{ Animatable(bgColor) }

    val size = remember(big) { if(big) 48.dp else 96.dp }
    val sizeAnim = remember{ Animatable(size,Dp.VectorConverter) }

    LaunchedEffect(key1 = big ){
//        colorAnim.snapTo(bgColor)
//        colorAnim.animateTo(bgColor,
//            tween(durationMillis = 3000,easing = FastOutLinearInEasing)
//        )
//        colorAnim.animateTo(bgColor,
//            tween(durationMillis = 3000,easing = { it })
//        )
//        colorAnim.animateTo(bgColor)

//        colorAnim.animateTo(bgColor, snap(1000))

//        colorAnim.animateTo(bgColor, keyframes {
//            //动画时长
//            durationMillis = 10000
//            //延迟
//            delayMillis = 3000
//            //设置  初始值 就有 速度曲线    这是一个关键帧
//            Color.Red at 0 with FastOutLinearInEasing
//            //150ms的时候 变量是 144dp
//            //用with 添加速度曲线 150ms 到300ms 后一段
//            Color.Yellow at 4000 with FastOutLinearInEasing
//            //如果不写 默认LinearEasing
//            Color.Green at 10000
//        })

//        sizeAnim.animateTo(size, spring(0.1f,StiffnessHigh))

        sizeAnim.animateTo(size, infiniteRepeatable( tween(),RepeatMode.Reverse,
            StartOffset(500, StartOffsetType.Delay))
        )
    }


    Box(modifier = Modifier
        .size(sizeAnim.value)
//        .size(100.dp)
        .background(colorAnim.value)
        .clickable(onClick = {
            isRun = true
            big = !big
        }, indication = null, interactionSource = remember {
            MutableInteractionSource()
        })
    ){}
}

@Preview
@Composable
fun UserPage2222(){

    val anim = remember{ Animatable(0.dp,Dp.VectorConverter) }
    val animY = remember{ Animatable(0.dp,Dp.VectorConverter) }
    val decay = exponentialDecay<Dp>()
    val scope = rememberCoroutineScope()

    var padding by remember{ mutableStateOf(anim.value) }
    LaunchedEffect(Unit ){
        delay(1000)
        try {
            var result = anim.animateDecay(4000.dp,decay)
            while(result.endReason == AnimationEndReason.BoundReached){
                result = anim.animateDecay(- result.endState.velocity,decay )
            }
        } catch (e: CancellationException) {

        }
    }
    LaunchedEffect(Unit ){
        delay(1000)
        animY.animateDecay(2000.dp,decay)
    }


    BoxWithConstraints() {
//        anim.updateBounds(0.dp,upperBound = maxWidth-100.dp)
        animY.updateBounds(upperBound = maxHeight-100.dp)

        val paddingX = remember(anim.value) {
            var usedValue = anim.value
            while(usedValue >= (maxWidth - 100.dp) * 2 ){
                usedValue -= (maxWidth -100.dp) * 2
            }

            if(usedValue < maxWidth - 100.dp){
                usedValue
            }else{
                (maxWidth -100.dp) * 2 - usedValue
            }
        }

        Row{
            Box(modifier = Modifier
                .padding(paddingX, animY.value, 0.dp, 0.dp)
                .size(100.dp)
                .background(Color.Green)
            )
        }
    }



}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun UserPage4444(){
    var big by remember { mutableStateOf(false) }
    //animateColorAsState 也可以做到  面向状态本身，无法设置初始值， 用的就是 实时值
    //Transition
    //两个放到 updateTransition 协程里性能更强
    val bigTransition = updateTransition(targetState = big, label = "big")

    val size by bigTransition.animateDp( {
        //Segment ： 翻译为  段,提供了 初始和结束的状态信息
        //由false 转换成 true
//        if(false isTransitioningTo true){ }
        when{
            !initialState && targetState -> spring()
            else->tween()
        }
    },label = "size"){ if(it) 96.dp else 48.dp }
    val corner by bigTransition.animateDp(label = "corner") { if(it) 0.dp else 18.dp }

    //animateTo 复用性相对较低

    var shown by remember { mutableStateOf(true) }
    Column(modifier = Modifier) {

        AnimatedVisibility(shown, enter = fadeIn(tween(2000),
            initialAlpha = 0.5f)){}

        //expand 扩张 从 默认从右下角开始扩张
        //clip 裁切效果， 默认true 裁切，会   控制内容  进行裁切，
        //              ， false     不会 控制内容
        AnimatedVisibility(shown, enter = expandIn(tween(5000),expandFrom = Alignment.TopStart,
            initialSize = { IntSize(it.width/2,it.height) })){

        }

        //横向 纵向展开
        AnimatedVisibility(shown, enter =expandHorizontally()){}
//        expandHorizontally {  }
//        expandVertically {  }


        //缩放效果 从哪里缩放
        AnimatedVisibility(shown, enter = scaleIn(transformOrigin =
        TransformOrigin(0f,0f))){}

        //可以使用+号做复杂动画
        AnimatedVisibility(shown, enter = fadeIn()+ expandIn() ){}

        AnimatedVisibility(shown, enter = fadeIn()+ expandIn() ,
            exit = fadeOut() + shrinkOut()
        ){

        }

//        AnimatedVisibility(shown,
//            enter = slideIn(tween(2000)) {
//                IntOffset(-it.width,-it.height)
////            IntOffset(-100,-100)
//            }){


        AnimatedVisibility(visible = shown, enter =
        expandIn(tween(5000),
            expandFrom = Alignment.TopStart,
            initialSize = { IntSize(it.width/2,it.height/2) },
            clip = false)
        ){

            Box(modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(corner))
                .background(Color.Green)
                .clickable {
                    big = !big
                }) {

            }
        }


        Button(onClick = { shown = !shown}){
            Text("btn")
        }

    }
}


@Preview
@Composable
fun UserPage555(){
    var shown by remember { mutableStateOf(true) }
    Column(modifier = Modifier) {
        Crossfade(targetState = shown) {
            if(it){
                Box(modifier = Modifier
                    .background(Color.Red)
                    .size(24.dp))
            }else{
                Box(modifier = Modifier
                    .background(Color.Green)
                    .size(48.dp))
            }
        }
        Button(onClick = { shown = !shown}){
            Text("btn")
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun UserPage6666(){
    var shown by remember { mutableStateOf(true) }
    Column(modifier = Modifier) {
        AnimatedContent(shown, transitionSpec = {
//            ContentTransform(fadeIn(), fadeOut())
            (fadeIn() with  fadeOut() using SizeTransform()).apply {
                targetContentZIndex = -1f
            }
        }) {
            if(it){
                Box(modifier = Modifier
                    .background(Color.Red)
                    .size(24.dp))
            }else{
                Box(modifier = Modifier
                    .background(Color.Green)
                    .size(48.dp))
            }
        }
        Button(onClick = { shown = !shown}){
            Text("btn")
        }
    }

}

@Preview
@Composable
fun UserPage777(){

    Column(modifier = Modifier.padding(top=30.dp)) {
        Box(modifier = Modifier.background(Color.Yellow)) {
            Text("abc", modifier = Modifier.layout { measurable, constraints ->
                //layout 只能修饰一个组件， 如果要精细修饰 组件 Layout()
                //只能测量自己，不能负责完整的测量, 在这个layout的内部 拿不到Text属性的，干涉不了内部文字


                val paddingPx = 10.dp.roundToPx()



                //测量
                val placeable = measurable.measure(constraints.copy(
                    maxWidth = constraints.maxWidth - paddingPx*2,
                    maxHeight = constraints.maxHeight - paddingPx*2
                ))
                //修改尺寸
                layout(placeable.width + paddingPx*2, placeable.height + paddingPx*2) {
                    //修改位置，内部便宜， 单位是像素
                    placeable.placeRelative(paddingPx, paddingPx)

                }


                //返回测量结果 一般不这么写
                //            object:MeasureResult{
                //                override val alignmentLines: Map<AlignmentLine, Int>
                //                    get() = TODO("Not yet implemented")
                //                override val height: Int
                //                    get() = TODO("Not yet implemented")
                //                override val width: Int
                //                    get() = TODO("Not yet implemented")
                //
                //                override fun placeChildren() {
                //                    TODO("Not yet implemented")
                //                }
                //
                //            }

            })
        }
    }

}

@Preview
@Composable
fun UserPage88888(){

    val modifier = Modifier.composed {
        //这里使用的时候会 执行多次 ，分别多次的  对象
        var padding by remember{ mutableStateOf(8.dp) }
        Modifier
            .size(padding)
            .clickable { padding = 0.dp }
    }

    var padding1 by remember{ mutableStateOf(8.dp) }
    val modifier1 = Modifier
        .size(padding1)
        .clickable { padding1 = 0.dp }

    Box(modifier = modifier)
    Text("abc",modifier)

}

fun Modifier.paddingJumpModifier() = composed {
    var padding by remember{ mutableStateOf(8.dp) }
    Modifier
        .size(padding)
        .clickable { padding = 0.dp }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun UserPage99999999(){

    Row{
        Box(
            Modifier
                .size(40.dp)
                .background(Color.Red)
                .weight(1f))
        Box(
            Modifier
                .size(40.dp)
                .background(Color.Green))

    }


    Box(Modifier
        .background(Color.Red)
        .size(80.dp)
        .combinedClickable(onLongClick = { Log.e("log", "onLongClick") }) {
            Log.e("log", "onClick")
        })

    Modifier.pointerInput(Unit){
        //半手动 检测
        detectTapGestures(onTap = {}, onDoubleTap = {}, onLongPress = {})

        //全手动 检测
        forEachGesture {
            awaitPointerEventScope {
                //可以定制 完整的  按下   抬起
                val down = awaitFirstDown()
            }
        }

    }





//    Box(Modifier.size(40.dp).padding(8.dp).background(Color.Blue))
//    Box(Modifier.background(Color.Red).requiredSize(80.dp).background(Color.Blue).requiredSize(40.dp))
//     Box(Modifier.size(40.dp).padding(20.dp).background(Color.Blue))
//
//    Modifier.then(object:DrawModifier{
//        override fun ContentDrawScope.draw() {
//
//        }
//    })
//
//    Modifier.drawWithContent {
//        drawContent()
//        drawCircle(Color.Red)
//    }
//
//
//    Box(Modifier.size(40.dp).drawWithContent {
//
//    }.background(Color.Blue))


//    CustomLayout{
//        Text("abc",Modifier.weightData(1f).weightData(2f))
//        Text("def")
//        Box{
////            Text("123",Modifier.weightData(1f))
//        }
//    }


    Column{
        Text("123")
        Box(
            Modifier
                .size(40.dp)
                .semantics(true) {
                    //true 表示合并到后代,
                    //设置true 表示 无障碍点击 ，会吧两个内容合并到一起，
                    //默认false 表示不合并， 点击 会分别读 两个文字
                    contentDescription = "大方块"
                }){
            Text("小方块")
        }
        Button({}){
            Text("abc",Modifier.semantics(true) {

                contentDescription = "abc"
            })
        }
    }





    Text("小方块",Modifier.onSizeChanged {

    })

    Modifier
        .padding(20.dp)
        .then(object : OnRemeasuredModifier {
            override fun onRemeasured(size: IntSize) {
                TODO("Not yet implemented")
            }
        })
        .padding(40.dp)

    var offsetX by remember{ mutableStateOf(0) }
    Modifier
        .onPlaced {
            offsetX = it.positionInParent().x.toInt()
        }
        .offset {
            IntOffset(offsetX, 0)
        }
        .size(40.dp)


    Modifier.onGloballyPositioned {  }

}


@Preview
@Composable
fun UserPage1211111(){

    var isV by remember{ mutableStateOf(true) }

    Column() {
        Button(onClick = { isV = false }) {
            Text("按钮")
        }

        if(isV){
            Box(
                Modifier
                    .size(20.dp)
                    .background(Color.Blue)){
                DisposableEffect(Unit){
                    Log.e("disposable","in")
                    onDispose {
                        Log.e("disposable","out")
                    }
                }
            }
        }
    }

    val scope = rememberCoroutineScope()
    Box(Modifier.clickable {
        scope.launch {

        }
    }){

    }

    val positionState = flow<Point> {  }



    var position by remember{ mutableStateOf(Point(0,0)) }
    LaunchedEffect(key1 = Unit){
        positionState.collect{
            position = it
        }
    }

    val prodiceState = produceState(initialValue = Point(0,0)){
        positionState.collect{
            value = it
        }

        awaitDispose {

        }
    }


    val name by remember{ mutableStateOf("abc") }
    val flow = snapshotFlow { name }
    LaunchedEffect(Unit){
        flow.collect{
            println(it)
        }
    }



}

@Composable
fun CustomLayout(modifier: Modifier = Modifier,content: @Composable CustomLayoutScope.()-> Unit){
    Layout({CustomLayoutScope.content()}, modifier ){measureable,constraints->
        measureable.forEach {
            val data = it.parentData as? Float

        }

        layout(100,100){

        }
    }
}


@LayoutScopeMarker  //不能穿透 ，只能在直接内部使用
object CustomLayoutScope{
    fun Modifier.weightData(float:Float) = then(object:ParentDataModifier{

        override fun Density.modifyParentData(parentData: Any?): Any? {
            //parentData 是 右边变 Modifier  的 参数weightData
            //是为了融合，是为了多个属性做融合的
            return float
        }
    })
}