package com.codedev.newsapplication.presentation.home

import androidx.paging.PagingData
import com.codedev.newsapplication.domain.utils.UiModel

data class HomeState(
    val pagingData: PagingData<UiModel>? = null,
    val query: String = "",
    val currentPosition: Int = -1,
    val trendingPagingData: PagingData<UiModel>? = null
)
