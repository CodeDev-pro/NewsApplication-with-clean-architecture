package com.codedev.newsapplication.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class EntityArticle(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
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
