package com.codedev.newsapplication.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codedev.newsapplication.presentation.home.components.ArticleItem
import com.codedev.newsapplication.presentation.ui.components.SearchField
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite

@Composable
fun SearchScreen(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayTone)
    ) {
        LazyColumn {
            item {
                SearchField()
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextWhite.copy(0.3f)))
            }
            items(30) {

            }
        }
    }
}