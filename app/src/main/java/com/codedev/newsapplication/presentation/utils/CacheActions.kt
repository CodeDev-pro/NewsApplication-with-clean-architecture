package com.codedev.newsapplication.presentation.utils

import com.codedev.newsapplication.domain.entities.EntityArticle

sealed class CacheActions {
    data class Save(val article: EntityArticle): CacheActions()
    data class Delete(val article: EntityArticle): CacheActions()
}