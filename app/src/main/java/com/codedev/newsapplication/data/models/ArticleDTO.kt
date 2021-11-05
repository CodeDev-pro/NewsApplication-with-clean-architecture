package com.codedev.newsapplication.data.models

import com.codedev.newsapplication.domain.entities.EntityArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    @SerialName("urlToImage")
    val image: String
)

fun ArticleDTO.toEntityArticle() : EntityArticle {
    return EntityArticle(
        this.author,
        this.content,
        this.description,
        this.publishedAt,
        this.source.name,
        this.title,
        this.url,
        this.image
    )
}