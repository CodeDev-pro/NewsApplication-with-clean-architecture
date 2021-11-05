package com.codedev.newsapplication.domain.utils

import com.codedev.newsapplication.domain.entities.EntityArticle

sealed class UiModel() {
    data class EntityArticleItem(val entityArticle: EntityArticle) : UiModel()
    data class TrendingEntityArticle(val entityArticle: EntityArticle) : UiModel()
}
