package com.codedev.newsapplication.data.models.article_models

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String? = null,
    val name: String
)