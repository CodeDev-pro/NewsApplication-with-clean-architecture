package com.codedev.newsapplication.presentation.home

import androidx.compose.ui.geometry.Offset
import com.codedev.newsapplication.domain.entities.EntityArticle

sealed class HomeEvents {
    data class Search(val query: String): HomeEvents()
    object GetTrendingArticles : HomeEvents()
    data class PopularArticleScroll(val position: Int, val offset: Int): HomeEvents()
    data class TrendingArticleScroll(val position: Int, val offset: Int): HomeEvents()
    data class SaveArticle(val article: EntityArticle): HomeEvents()
    data class ShareArticle(val article: EntityArticle): HomeEvents()
    data class DeleteArticle(val article: EntityArticle): HomeEvents()
}