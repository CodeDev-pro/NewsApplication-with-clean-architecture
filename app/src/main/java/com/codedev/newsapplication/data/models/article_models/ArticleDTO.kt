package com.codedev.newsapplication.data.models.article_models

import com.codedev.newsapplication.domain.entities.EntityArticle
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
    val author: String = "Unknown",
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)

fun ArticleDTO.toEntityArticle() : EntityArticle {
    return EntityArticle(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source.name,
        url = this.url,
        title = this.title,
        urlToImage = this.urlToImage,
        isSaved = false
    )
}