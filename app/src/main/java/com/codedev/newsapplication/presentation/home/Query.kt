package com.codedev.newsapplication.presentation.home

data class Query(
    val value: String
)

val queries = listOf<Query>(
    Query("Sports"),
    Query("Entertainment"),
    Query("Trump"),
    Query("USA"),
    Query("Climate Change"),
    Query("Education"),
    Query("Religion"),
    Query("Seventh Day Adventist"),
    Query("Space"),
    Query("Science"),
    Query("Others"),
)
