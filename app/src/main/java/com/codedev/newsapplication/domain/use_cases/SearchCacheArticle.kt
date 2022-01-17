package com.codedev.newsapplication.domain.use_cases

import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.repositories.NewsRepository

class SearchCacheArticle(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(query: String) = repository.searchArticle(query)
}