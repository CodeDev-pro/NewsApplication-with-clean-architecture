package com.codedev.newsapplication.presentation.home

import androidx.compose.ui.geometry.Offset
import androidx.paging.PagingData
import com.codedev.newsapplication.domain.utils.UiModel

data class HomeState(
    val popularData: ListState = ListState(),
    val query: String = "",
    val trendingData: ListState = ListState()
)

data class ListState(
    val pagingData: PagingData<UiModel>? = null,
    val offset: Int = 0,
    val position: Int = 0
)
