package com.oncescaffold.ui.page

import android.graphics.Camera
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.oncescaffold.R
import kotlin.math.roundToInt


@Preview
@Composable
fun UserPage123123(){
//    val image = ImageBitmap.imageResource(R.mipmap.tpic)
//    Canvas(modifier = Modifier.size(80.dp).padding(20.dp).graphicsLayer {
//        rotationX = 45f
//        rotationY = 45f
//                                                                        }, onDraw = {
//         drawImage(image,dstSize= IntSize(image.width,image.height))
//    })

//    val image = ImageBitmap.imageResource(R.mipmap.tpic)
//    val paint by remember{ mutableStateOf(Paint()) }
//    val rotationAnimation = remember { Animatable(0f) }
//    val camera by remember{ mutableStateOf(Camera()) }
//
//    LaunchedEffect(Unit) {
//        rotationAnimation.animateTo(360f, infiniteRepeatable(tween(2000)))
//    }
//    Box(modifier = Modifier.padding(100.dp)){
//        Canvas(modifier = Modifier.size(image.width.dp,image.height.dp)){
//            drawIntoCanvas {
//                it.translate(size.width/2,size.height/2)
//                it.rotate(-45f)
//                camera.save()
//                camera.rotateX(rotationAnimation.value)
//                camera.applyToCanvas(it.nativeCanvas)
//                camera.restore()
//                it.rotate(45f)
//                it.translate(- size.width/2,- size.height/2)
//                it.drawImageRect(image,dstSize= IntSize(size.width.roundToInt(),size.height.roundToInt()),
//                    paint = paint)
//            }
//        }
//    }
    CustomImage()

}

@Preview
@Composable
fun CustomImage() {
    val image = ImageBitmap.imageResource(R.mipmap.tpic)
    val paint by remember { mutableStateOf(Paint()) }
    val rotationAnimatable = remember { Animatable(0f) }
    val camera by remember { mutableStateOf(Camera()) }
    LaunchedEffect(Unit) {
        rotationAnimatable.animateTo(360f, infiniteRepeatable(tween(2000)))
    }
    Box(Modifier.padding(50.dp)) {
        Canvas(Modifier.size(100.dp)/*.graphicsLayer {
      rotationX = 45f
      rotationY = 45f
    }*/) {
            drawIntoCanvas {
                it.translate(size.width / 2, size.height / 2)
                it.rotate(-45f)
                camera.save()
                camera.rotateX(rotationAnimatable.value)
                camera.applyToCanvas(it.nativeCanvas)
                camera.restore()
                it.rotate(45f)
                it.translate(- size.width / 2, - size.height / 2)
                it.drawImageRect(image, dstSize = IntSize(size.width.roundToInt(),
                    size.height.roundToInt()), paint = paint)
            }
        }
    }
}

//@Preview
//@Composable
//fun CustomText(){
//    val image = ImageBitmap.imageResource(R.mipmap.ic_launcher)
//    Text(text = "abc", Modifier.drawWithContent {
//        drawRect(Color.Yellow)
//        drawContent()
//        drawLine(Color.Red, Offset(0f,size.height/2),Offset(size.width,size.height/2),2.dp.toPx())
//
//        rotate(30f){
//            drawImage(image)
//        }
//
//    })
//}

