package com.codedev.newsapplication.domain.entities

import java.io.Serializable

data class EntityArticle(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val isSaved: Boolean
) : Serializable
