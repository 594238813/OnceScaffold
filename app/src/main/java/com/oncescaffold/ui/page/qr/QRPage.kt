package com.oncescaffold.ui.page.qr

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.*
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.imePadding

import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.ui.graphics.Color

private val listItems = List(40) { randomSampleImageUrl(it) }

private val rangeForRandom = (0..100000)
fun randomSampleImageUrl(
    seed: Int = rangeForRandom.random(),
    width: Int = 300,
    height: Int = width,
): String {
    return "https://picsum.photos/seed/$seed/$width/$height"
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QRPage() {
    //accompanist-insets-ui 包里的 Scaffold
    Scaffold(
        topBar = {
            // We use TopAppBar from accompanist-insets-ui which allows us to provide
            // content padding matching the system bars insets.
            TopAppBar(
                title = { Text("title") },
                backgroundColor = MaterialTheme.colors.surface,
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                modifier = Modifier.fillMaxWidth(),
                elevation = 0.dp
            )
        },
        bottomBar = {
            Surface(elevation = 1.dp ) {
                val text = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    placeholder = { Text(text = "Watch me animate...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        //把键盘推上去的动画 API 30+
                        .navigationBarsPadding().imePadding()
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { contentPadding ->
        Column{
            // We apply the contentPadding passed to us from the Scaffold
            LazyColumn(
                contentPadding = contentPadding,
                reverseLayout = true,
                modifier = Modifier
                    .weight(1f)
                    .imeNestedScroll()
            ) {
                items(listItems) { imageUrl ->
                    ListItem(imageUrl, Modifier.fillMaxWidth())
                }
            }
        }
    }
}


@Composable
fun ListItem(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Row(modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(4.dp)),
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = "Text",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}
