package com.codedev.newsapplication.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import com.codedev.newsapplication.R
import com.codedev.newsapplication.presentation.ui.theme.BoldOblique
import com.codedev.newsapplication.presentation.ui.theme.BookOblique
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite
import com.skydoves.landscapist.coil.LocalCoilImageLoader

private const val TAG = "TrendingNewItem"

@Preview(showBackground = true)
@Composable
fun TrendingArticleItem() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .build()
    Box(
        modifier = Modifier
            .padding(5.dp)
            .width(200.dp)
            .height(350.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        //Log.d(TAG, "NewsItem: ${painter.loadState}")
        CompositionLocalProvider(LocalCoilImageLoader provides imageLoader) {
            Image(
                painter = painterResource(id = R.drawable.bitcoin),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop,
                /*loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) {
                        CircularProgressIndicator(
                            progress = it.progress
                        )
                    }
                },
                failure = {

                }*/
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )

        Text(
            text = "Hello this is some dummy text to try out the News application make sure you enjoy",
            style = MaterialTheme.typography.body1.copy(color = TextWhite, fontSize = 19.sp, fontWeight = BoldOblique),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        ConstraintLayout(
            modifier = Modifier.align(Alignment.TopStart)
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            val (saveIcon, sourceText) = createRefs()
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .constrainAs(sourceText) {
                        top.linkTo(saveIcon.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(saveIcon.bottom)
                    }
                    .clip(RoundedCornerShape(5.dp))
                    .background(DarkGrayTone.copy(alpha = 0.5f))
                    .clickable {  }
                    .padding(5.dp)
            ) {
                Text(
                    text = "CNN",
                    style = MaterialTheme.typography.body1.copy(color = TextWhite),
                    modifier = Modifier.padding(5.dp)
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(5.dp)
                    .constrainAs(saveIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = DarkGrayTone.copy(alpha = 0.6f))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = TextWhite,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}