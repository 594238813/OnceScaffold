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
//        anim.snapTo(if(big) 200.dp else 0.dp)      //????????????
//        anim.animateTo(size1,TweenSpec(easing = FastOutLinearInEasing))   //????????????
//        slowColor.animateTo(bgColor, SnapSpec(2000))   //??? snapTo ???????????? ??????????????????
//        slowColor.animateTo(bgColor, snap(2000))   //
//        slowColor.animateTo(bgColor,TweenSpec(durationMillis = 9000,
//            delay = 5000,
//            easing = LinearOutSlowInEasing))

        //????????? ?????????
//        anim.animateTo(size1, keyframes {
//            //????????????
//            durationMillis = 450
//            //??????
//            delayMillis = 100
//
//            //??????  ????????? ?????? ????????????
//            48.dp at 0 with FastOutLinearInEasing
//            //150ms????????? ????????? 144dp
//            //???with ?????????????????? 150ms ???300ms ?????????
//            144.dp at 150 with FastOutLinearInEasing
//
//            //???????????? ??????LinearEasing
//            20.dp at 300
//        })


        //animateTo  ???????????????  ????????????
//        anim.animateTo(size1, spring(0.1f,Spring.StiffnessLow) ,2000.dp)

        //repeatable
        //??????????????? ????????????
        //??????????????? ????????? ?????? , ??????????????? ?????????Spring(), ??? repeatable() ????????????
        //??????????????? ???????????? ?????????????????? ??????
        //?????????????????? ??????  ????????????????????? ???????????????
        //?????? ?????????
        //??????????????? ???????????????????????????,
        // ???????????????????????????  StartOffsetType.Delay  ????????????
        //   ???????????? StartOffsetType.FastForward   ???????????? ?????????offsetMillis ????????????
//        anim.animateTo(size1, repeatable(3, tween(),RepeatMode.Reverse
//        , StartOffset(500, StartOffsetType.Delay)
//        ))

        //??????????????? ????????????
        //????????????  ????????????  ???boolean ?????? ??????
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
//        anim.snapTo(if(big) 200.dp else 0.dp)      //????????????
//        anim.animateTo(size1,TweenSpec(easing = FastOutLinearInEasing))   //????????????
//        offsetAnim.animateTo(offset,TweenSpec(easing = LinearOutSlowInEasing))
//        offsetAnim.animateTo(offset, tween())
//        offsetAnim.animateTo(offset, snap())  //?????????


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
//            //????????????
//            durationMillis = 10000
//            //??????
//            delayMillis = 3000
//            //??????  ????????? ?????? ????????????    ?????????????????????
//            Color.Red at 0 with FastOutLinearInEasing
//            //150ms????????? ????????? 144dp
//            //???with ?????????????????? 150ms ???300ms ?????????
//            Color.Yellow at 4000 with FastOutLinearInEasing
//            //???????????? ??????LinearEasing
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
    //animateColorAsState ???????????????  ????????????????????????????????????????????? ???????????? ?????????
    //Transition
    //???????????? updateTransition ?????????????????????
    val bigTransition = updateTransition(targetState = big, label = "big")

    val size by bigTransition.animateDp( {
        //Segment ??? ?????????  ???,????????? ??????????????????????????????
        //???false ????????? true
//        if(false isTransitioningTo true){ }
        when{
            !initialState && targetState -> spring()
            else->tween()
        }
    },label = "size"){ if(it) 96.dp else 48.dp }
    val corner by bigTransition.animateDp(label = "corner") { if(it) 0.dp else 18.dp }

    //animateTo ?????????????????????

    var shown by remember { mutableStateOf(true) }
    Column(modifier = Modifier) {

        AnimatedVisibility(shown, enter = fadeIn(tween(2000),
            initialAlpha = 0.5f)){}

        //expand ?????? ??? ??????????????????????????????
        //clip ??????????????? ??????true ????????????   ????????????  ???????????????
        //              ??? false     ?????? ????????????
        AnimatedVisibility(shown, enter = expandIn(tween(5000),expandFrom = Alignment.TopStart,
            initialSize = { IntSize(it.width/2,it.height) })){

        }

        //?????? ????????????
        AnimatedVisibility(shown, enter =expandHorizontally()){}
//        expandHorizontally {  }
//        expandVertically {  }


        //???????????? ???????????????
        AnimatedVisibility(shown, enter = scaleIn(transformOrigin =
        TransformOrigin(0f,0f))){}

        //????????????+??????????????????
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
                //layout ??????????????????????????? ????????????????????? ?????? Layout()
                //????????????????????????????????????????????????, ?????????layout????????? ?????????Text????????????????????????????????????


                val paddingPx = 10.dp.roundToPx()



                //??????
                val placeable = measurable.measure(constraints.copy(
                    maxWidth = constraints.maxWidth - paddingPx*2,
                    maxHeight = constraints.maxHeight - paddingPx*2
                ))
                //????????????
                layout(placeable.width + paddingPx*2, placeable.height + paddingPx*2) {
                    //?????????????????????????????? ???????????????
                    placeable.placeRelative(paddingPx, paddingPx)

                }


                //?????????????????? ??????????????????
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
        //???????????????????????? ???????????? ??????????????????  ??????
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
        //????????? ??????
        detectTapGestures(onTap = {}, onDoubleTap = {}, onLongPress = {})

        //????????? ??????
        forEachGesture {
            awaitPointerEventScope {
                //???????????? ?????????  ??????   ??????
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
                    //true ?????????????????????,
                    //??????true ?????? ??????????????? ???????????????????????????????????????
                    //??????false ?????????????????? ?????? ???????????? ????????????
                    contentDescription = "?????????"
                }){
            Text("?????????")
        }
        Button({}){
            Text("abc",Modifier.semantics(true) {

                contentDescription = "abc"
            })
        }
    }





    Text("?????????",Modifier.onSizeChanged {

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
            Text("??????")
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


@LayoutScopeMarker  //???????????? ??????????????????????????????
object CustomLayoutScope{
    fun Modifier.weightData(float:Float) = then(object:ParentDataModifier{

        override fun Density.modifyParentData(parentData: Any?): Any? {
            //parentData ??? ????????? Modifier  ??? ??????weightData
            //???????????????????????????????????????????????????
            return float
        }
    })
}