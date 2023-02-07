package com.oncescaffold.ui.page.me

import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.oncescaffold.R
import com.oncescaffold.ui.page.main.*
import com.oncescaffold.ui.theme.*
import com.oncescaffold.widgets.banner
import kotlinx.coroutines.delay


@Preview
@Composable
fun UserPage(){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color_f7f7f7)){
        UserPage_Info()
    }

}

@Preview
@Composable
private fun UserPage_Info(){
    Column(){
        Box(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())){
            UserPage_InfoTopBg()
            Column(modifier = Modifier.statusBarsPadding()){
                UserPage_InfoTopInfo()
                Spacer(modifier = Modifier.height(24.dp))
                UserPage_InfoSubList()
                Spacer(modifier = Modifier.height(12.dp))
                UserPage_InfoSubList1()
                Spacer(modifier = Modifier.height(12.dp))
                UserPage_InfoBanner()
                Spacer(modifier = Modifier.height(12.dp))
                UserPage_InfoSetting()
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

}

@Composable
private fun UserPage_InfoTopBg(){
    //背景
    //目前compose 不支持点9图 使用第三方加载
    Image(rememberImagePainter(
        ContextCompat.getDrawable(LocalContext.current,R.mipmap.pic_main_me_top)
    ), contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(375 / 275f),
        contentScale = ContentScale.FillBounds
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF206AF5)
@Composable
private fun UserPage_InfoTopInfo(){
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

        val (info,msgBox) = createRefs()

        Box(modifier = Modifier
            .padding(top = 12.dp, end = 12.dp)
            .constrainAs(msgBox) {
                top.linkTo(parent.top)
                end.linkTo(parent.absoluteRight)
            }){

            Box(modifier = Modifier
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        Color(0xffff0000),
                        8.dp.toPx() / 2,
                        center = Offset(drawContext.size.width, 0f)
                    )
                }
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.icon_homemessage),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        val navController = LocalGlobalNavController.current
        ConstraintLayout(modifier = Modifier
            .constrainAs(info) {
                top.linkTo(parent.top)
                start.linkTo(parent.absoluteLeft)
                end.linkTo(msgBox.start)
                width = Dimension.fillToConstraints
            }
            .padding(start = 12.dp, top = 28.dp, end = 20.dp)
        ){
            val (head,name) = createRefs()

            Surface(modifier = Modifier.constrainAs(head){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.fillToConstraints
            }.clickable {
                //点击头像跳转
                navController.navigate("LoginPage")
            } ,
                shape = CircleShape
            ){
                Image(
                    painter = painterResource(id = R.mipmap.tpic),
                    contentDescription = null,
                    modifier = Modifier.size(55.dp)
                )
            }

            Box(modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(head.end)
                    top.linkTo(head.top)
                    bottom.linkTo(head.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .padding(start = 16.dp)){
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween){
                    Text("这里是昵称这里是昵称这里是昵称这里是昵称这里是昵称这里是昵称这里是昵称",
                        color = Color.White,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text("这里是内容这里是内容这里是内容这里是内容这里是内容这里是内容这里是内容",
                        color = Color.White,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF206AF5)
@Composable
private fun UserPage_InfoSubList(){

    val list = arrayListOf(
        SubListBean.cardservice,
        SubListBean.ecard,
        SubListBean.travellist,
        SubListBean.wallet,
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 12.dp, end = 12.dp) ){
        list.forEach { bean->
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true),
                contentAlignment = Alignment.Center){
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Image(painter = painterResource(id = bean.icon)
                        ,contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(bean.title,
                        color = Color.White,
                        fontSize = 12.sp)
                }
            }
        }
    }
}

private sealed class SubListBean(
    var title: String,
    @DrawableRes var icon: Int,
) {
    object cardservice: SubListBean( "出行账单", R.mipmap.icon_cardservice )
    object ecard: SubListBean(  "卡服务", R.mipmap.icon_ecard)
    object travellist: SubListBean ( "电子市民卡",R.mipmap.icon_travellist )
    object wallet: SubListBean( "我的钱包", R.mipmap.icon_wallet )

    object yygh : SubListBean( "预约挂号", R.mipmap.icon_yygh )
    object bgcx : SubListBean(  "报告查询", R.mipmap.icon_bgcx)
    object zsjf : SubListBean ( "掌上缴费",R.mipmap.icon_zsjf )
    object pdhz : SubListBean( "排队候诊", R.mipmap.icon_pdhz )
}

@Preview
@Composable
private fun UserPage_InfoSubList1(){
    val list = arrayListOf(
        SubListBean.yygh,
        SubListBean.bgcx,
        SubListBean.zsjf,
        SubListBean.pdhz,
    )
    Card(modifier = Modifier
        .padding(start = 12.dp, end = 12.dp)
        .fillMaxWidth()
       ,
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp) {
        Row( modifier = Modifier.padding( top = 8.dp, bottom = 8.dp)){
            list.forEach { bean->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                    contentAlignment = Alignment.Center){
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Image(painter = painterResource(id = bean.icon)
                            ,contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(bean.title, color = color_1f3456, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserPage_InfoBanner(){
    banner()
}

@Preview
@Composable
private fun UserPage_InfoSetting(){

    Card(modifier = Modifier
        .padding(start = 12.dp, end = 12.dp)
        .fillMaxWidth(),
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp)
    ){
        Column(modifier = Modifier.fillMaxWidth()){

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = R.mipmap.icon_me_setting),contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("设置",fontSize = 15.sp,color = color_1f3456)
                }
                Image(painter = painterResource(id = R.mipmap.icon_me_right),contentDescription = null)
            }
            Spacer(modifier = Modifier
                .padding(start = 44.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color_eeeeee))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = R.mipmap.icon_me_callservice),contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("电话客服",fontSize = 15.sp,color = color_1f3456)
                }
                Image(painter = painterResource(id = R.mipmap.icon_me_right),contentDescription = null)
            }
            Spacer(modifier = Modifier
                .padding(start = 44.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color_eeeeee))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = R.mipmap.icon_me_aboutus),contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("关于我们",fontSize = 15.sp,color = color_1f3456)
                }
                Image(painter = painterResource(id = R.mipmap.icon_me_right),contentDescription = null)
            }
            Spacer(modifier = Modifier
                .padding(start = 44.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color_eeeeee))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = R.mipmap.icon_me_share),contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("分享给好友",fontSize = 15.sp,color = color_1f3456)
                }
                Image(painter = painterResource(id = R.mipmap.icon_me_right),contentDescription = null)
            }
        }
    }

}