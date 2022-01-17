package com.codedev.newsapplication.presentation.favourites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codedev.newsapplication.presentation.home.components.ArticleItem
import com.codedev.newsapplication.presentation.ui.components.CustomChip
import com.codedev.newsapplication.presentation.ui.components.SearchField
import com.codedev.newsapplication.presentation.ui.theme.TextWhite

private const val TAG = "FavouriteScreen"

@Composable
fun FavouriteScreen(color: Color, viewModel: FavouriteViewModel = hiltViewModel()) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        Log.d(TAG, "FavouriteScreen: ")
        viewModel.execute(FavouriteEvents.GetAllArticles)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                FavouriteTitleSection()
                SearchField(
                    value = "",
                    setValue = {}
                )
                FilterSection()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(TextWhite.copy(0.3f))
                )
            }
            if (state.loading) {
                item {
                    CircularProgressIndicator()
                }
            } else if (!state.loading and !state.items.isNullOrEmpty()) {
                state.items?.let {
                    items(it.size) { index ->
                        ArticleItem(article = it[index], onDismiss = {})
                    }
                }
            }
        }
    }
}

@Composable
fun FavouriteTitleSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Favourite", style = MaterialTheme.typography.h1)
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun FilterSection(

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 7.5.dp, end = 7.5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomChip(
                text = "Ascending",
                onSelect = {
                }, selected = true
            )
            CustomChip(
                text = "Descending",
                selected = false,
                onSelect = {

                })
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomChip(
                text = "Title",
                selected = true,
                onSelect = {

                })
            CustomChip(
                text = "Date",
                selected = false,
                onSelect = { })
        }
    }
}

