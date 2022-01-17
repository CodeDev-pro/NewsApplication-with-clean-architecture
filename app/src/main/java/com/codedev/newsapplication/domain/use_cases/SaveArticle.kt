package com.codedev.newsapplication.domain.use_cases

import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.repositories.NewsRepository

class SaveArticle(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: EntityArticle, callback: suspend (Boolean, String?) -> Unit) {
        try{
            repository.saveArticle(article)
            callback(true, null)
        }catch(e: Exception) {
            callback(false, e.message)
        }
    }
}
