package com.codedev.newsapplication.presentation.favourites

import com.codedev.newsapplication.domain.entities.EntityArticle

data class FavouriteState(
    val items: List<EntityArticle>? = null,
    val loading: Boolean = false,
    val error: String = "",
    val isEmpty: Boolean = false
)
