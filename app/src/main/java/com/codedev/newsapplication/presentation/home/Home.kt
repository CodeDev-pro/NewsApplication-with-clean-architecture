package com.codedev.newsapplication.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import com.codedev.newsapplication.R
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.utils.UiModel
import com.codedev.newsapplication.presentation.home.components.ArticleItem
import com.codedev.newsapplication.presentation.home.components.CustomDatePicker
import com.codedev.newsapplication.presentation.home.components.TrendingArticleItem
import com.codedev.newsapplication.presentation.ui.components.*
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTint
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite
import com.codedev.newsapplication.presentation.utils.CacheActions
import com.codedev.newsapplication.presentation.utils.getCurrentDate
import com.codedev.newsapplication.presentation.utils.toDateFormat
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val TAG = "Home"

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(
    color: Color,
    viewModel: HomeViewModel = hiltViewModel(),
    scope: CoroutineScope,
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val popularArticles: LazyPagingItems<UiModel> = viewModel.pagingFlow.collectAsLazyPagingItems()
    val trendingArticles: LazyPagingItems<UiModel> =
        viewModel.trendingPagingFlow.collectAsLazyPagingItems()
    val popularArticleState = rememberLazyListState()
    val trendingArticleState = rememberLazyListState()

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                scope.launch {
                    val popularState = viewModel.state.value.popularData
                    val trendingState = viewModel.state.value.trendingData
                    if (popularArticles.itemCount > popularState.position) {
                        popularArticleState.scrollToItem(popularState.position, popularState.offset)
                    }
                    if (trendingArticles.itemCount > trendingState.position) {
                        trendingArticleState.scrollToItem(
                            trendingState.position,
                            trendingState.offset
                        )
                    }
                }
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.execute(
                    HomeEvents.TrendingArticleScroll(
                        position = trendingArticleState.firstVisibleItemIndex,
                        offset = trendingArticleState.firstVisibleItemScrollOffset
                    )
                )
                viewModel.execute(
                    HomeEvents.PopularArticleScroll(
                        position = popularArticleState.firstVisibleItemIndex,
                        offset = popularArticleState.firstVisibleItemScrollOffset
                    )
                )
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    var currentQuery by remember {
        mutableStateOf(queries[1].value.lowercase())
    }
    var expandedItemId by remember {
        mutableStateOf("")
    }

    val dialogState = rememberMaterialDialogState()
    val datePicked = remember {
        mutableStateOf(getCurrentDate())
    }

    LaunchedEffect(key1 = currentQuery) {
        viewModel.execute(HomeEvents.Search(currentQuery))
    }

    LaunchedEffect(key1 = true) {
        viewModel.events.collect {
            when (it) {
                is UiEvents.CacheMessage -> {

                }
            }
        }
    }

    CustomDatePicker(dialogState = dialogState, datePicked = datePicked)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayTone),
        state = popularArticleState
    ) {
        item {
            TopSection(dialogState, datePicked)
            Spacer(modifier = Modifier.height(5.dp))
            QuerySection(
                currentQuery = currentQuery,
                onQueryChanged = {
                    currentQuery = it
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            TrendingSection(
                trendingItems = trendingArticles,
                state = trendingArticleState
            ) {
                navController.currentBackStackEntry?.savedStateHandle?.set("article", it)
                navController.navigate("webpage")
            }
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
        items(popularArticles) { article ->
            when (article) {
                is UiModel.EntityArticleItem -> {
                    ArticleItem(article = article.entityArticle, onMoreClick = {
                        Log.d(TAG, "HomeScreen: more clicked, ${article.entityArticle.content}")
                        expandedItemId = article.entityArticle.url
                    }, onDismiss = {
                        expandedItemId = ""
                    }, expandedItem = expandedItemId, onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("article", it)
                        navController.navigate("webpage")
                    }, onCacheAction = {
                        when (it) {
                            is CacheActions.Save -> viewModel.execute(HomeEvents.SaveArticle(it.article))
                            is CacheActions.Delete -> viewModel.execute(HomeEvents.DeleteArticle(it.article))
                        }
                    })

                }
                else -> {
                }
            }
        }
        popularArticles.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        LoadingView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingView() }
                }
                loadState.refresh is LoadState.Error -> {
                    val errorObject = popularArticles.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = errorObject.error.localizedMessage!!,
                            modifier = Modifier.fillMaxSize(),
                            onClick = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val errorObject = popularArticles.loadState.append as LoadState.Error
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

@ExperimentalCoroutinesApi
@Composable
fun QuerySection(currentQuery: String, onQueryChanged: (String) -> Unit) {

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
        items(queries.size) { index ->
            CustomChip(
                text = queries[index].value,
                onSelect = {
                    if (queries[index].value != currentQuery) {
                        onQueryChanged(queries[index].value)
                    }
                },
                selected = currentQuery == queries[index].value
            )
        }
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun TopSection(dialogState: MaterialDialogState, datePicked: MutableState<LocalDate>) {
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
            Text(
                text = datePicked.value.toDateFormat(),
                style = MaterialTheme.typography.h6,
                maxLines = 2
            )
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
            onClick = {
                dialogState.show()
            },
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(DarkGrayTint.copy(0.1f))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_date),
                contentDescription = null,
                tint = TextWhite
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun TrendingSection(
    trendingItems: LazyPagingItems<UiModel>,
    state: LazyListState,
    onItemClick: (EntityArticle) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

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
            modifier = Modifier.fillMaxSize(),
            state = state
        ) {
            item {
                Spacer(modifier = Modifier.width(10.dp))
            }
            items(trendingItems) { article ->
                when (article) {
                    is UiModel.TrendingEntityArticle -> {
                        TrendingArticleItem(article = article.entityArticle, onClick = {
                            onItemClick(it)
                        })
                    }
                    else -> {

                    }
                }
            }
            trendingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(modifier = Modifier.width(screenWidth.dp)) {
                                LoadingView(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                            }

                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier.fillMaxHeight()) }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val errorObject = trendingItems.loadState.refresh as LoadState.Error
                        item {
                            Box(modifier = Modifier.width(screenWidth.dp)) {
                                ErrorItem(
                                    message = errorObject.error.localizedMessage!!,
                                    modifier = Modifier.fillMaxSize(),
                                    onClick = { retry() }
                                )
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val errorObject = trendingItems.loadState.append as LoadState.Error
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
}
