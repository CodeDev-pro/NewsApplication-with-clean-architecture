package com.codedev.newsapplication.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import com.codedev.newsapplication.R
import com.codedev.newsapplication.presentation.home.components.ArticleItem
import com.codedev.newsapplication.presentation.home.components.TrendingArticleItem
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTint
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.coil.LocalCoilImageLoader

private const val TAG = "Home"

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayTone)
    ) {
        item {
            TopSection()
            TrendingSection()
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Popular",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(15.dp)
                )
                Box(
                    Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TextWhite)
                )
            }
        }
        items(40) {
            ArticleItem()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(10.dp).fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "Friday, June 16th", style = MaterialTheme.typography.h6, maxLines = 2)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Daily Feed",
                style = MaterialTheme.typography.h2.copy(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(DarkGrayTint.copy(0.1f))
                .clickable {  }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_date),
                contentDescription = null,
                tint = TextWhite
            )
        }
    }
}

@Composable
fun TrendingSection() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Trending",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
        )
        Box(
            Modifier
                .padding(vertical = 2.dp, horizontal = 10.dp)
                .fillMaxWidth()
                .height(2.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(TextWhite)
        )
        LazyRow(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.width(10.dp))
            }
            items(30) {
                TrendingArticleItem()
            }
        }
    }
}

@Composable
fun ChipSection() {
    
}
