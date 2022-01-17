package com.codedev.newsapplication.presentation.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.codedev.newsapplication.R
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.presentation.ui.components.CustomChip
import com.codedev.newsapplication.presentation.ui.theme.BoldOblique
import com.codedev.newsapplication.presentation.ui.theme.BookOblique
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite
import com.codedev.newsapplication.presentation.utils.CacheActions
import com.skydoves.landscapist.coil.LocalCoilImageLoader

private const val TAG = "TrendingNewItem"

@ExperimentalCoilApi
@Composable
fun TrendingArticleItem(
    textColor: Color = TextWhite,
    article: EntityArticle,
    onClick: (EntityArticle) -> Unit = {},
    onCacheAction: (CacheActions) -> Unit = {}
) {
    val painter = rememberImagePainter(data = article.urlToImage, builder = {
        crossfade(true)
        error(R.drawable.ic_weather)
    })
    Box(
        modifier = Modifier
            .padding(5.dp)
            .width(200.dp)
            .height(350.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                onClick(article)
            }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
        )
        when (painter.state) {
            is ImagePainter.State.Success -> {
                Log.d(TAG, "ArticleItem: ${painter.state}")
            }
            is ImagePainter.State.Loading -> {
                Log.d(TAG, "ArticleItem: ${painter.state}")
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = TextWhite)
                }
            }
            is ImagePainter.State.Error -> {
                Log.d(TAG, "ArticleItem: ${painter.state}")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "An unknown error occurred",
                        style = MaterialTheme.typography.body1.copy(color = textColor)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CustomChip(
                        text = "Retry",
                        onSelect = {}
                    )
                }
            }
            else -> {
                Log.d(TAG, "ArticleItem: ${painter.state}")
                Box(modifier = Modifier.fillMaxSize())
            }
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
            text = article.title,
            style = MaterialTheme.typography.body1.copy(color = TextWhite, fontSize = 22.sp, fontWeight = BoldOblique),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(DarkGrayTone.copy(alpha = 0.5f))
                    .clickable { }
                    .padding(5.dp)
            ) {
                Text(
                    text = article.source,
                    style = MaterialTheme.typography.body1.copy(color = TextWhite),
                    modifier = Modifier.padding(5.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = {
                          if(article.isSaved) onCacheAction(CacheActions.Delete(article))
                          else onCacheAction(CacheActions.Save(article))
                },
                modifier = Modifier
                    .padding(5.dp)
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