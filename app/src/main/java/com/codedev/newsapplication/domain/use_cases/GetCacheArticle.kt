package com.codedev.newsapplication.domain.use_cases

import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.repositories.NewsRepository

class GetCacheArticle(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(id: Int, callback: suspend (Boolean, String?) -> Unit) {
        try {
            repository.getArticle(id)
            callback(true, null)
        } catch (e: Exception) {
            callback(false, e.message)
        }
    }
}