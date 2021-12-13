package com.codedev.newsapplication.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import com.codedev.newsapplication.R
import com.codedev.newsapplication.domain.utils.UiModel
import com.codedev.newsapplication.presentation.home.components.ArticleItem
import com.codedev.newsapplication.presentation.home.components.TrendingArticleItem
import com.codedev.newsapplication.presentation.ui.components.CustomChip
import com.codedev.newsapplication.presentation.ui.components.ErrorItem
import com.codedev.newsapplication.presentation.ui.components.LoadingView
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTint
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite
import com.codedev.newsapplication.presentation.utils.getCurrentTime
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TAG = "Home"

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(
    color: Color,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val items = viewModel.pagingFlow.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayTone)
    ) {
        item {
            TopSection()
            Spacer(modifier = Modifier.height(5.dp))
            QuerySection()
            Spacer(modifier = Modifier.height(5.dp))
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
        items(items) { item ->
            when(item) {
                is UiModel.EntityArticleItem -> {
                    ArticleItem(article = item.entityArticle)
                }
                else -> {}
            }
        }
        items.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingView() }
                }
                loadState.refresh is LoadState.Error -> {
                    val errorObject = items.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = errorObject.error.localizedMessage!!,
                            modifier = Modifier.fillMaxSize(),
                            onClick = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val errorObject = items.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = errorObject.error.localizedMessage!!,
                            onClick = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuerySection() {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth()
            .padding(start = 7.5.dp, end = 7.5.dp),
    ) {
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        items(queries.size) { item ->
            CustomChip(
                text = queries[item].value,
                onSelect = { },
                selected = queries[item].value.startsWith("S")
            )
        }
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = getCurrentTime(), style = MaterialTheme.typography.h6, maxLines = 2)
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
                .clickable { }
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
        Spacer(modifier = Modifier.height(4.dp))
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
