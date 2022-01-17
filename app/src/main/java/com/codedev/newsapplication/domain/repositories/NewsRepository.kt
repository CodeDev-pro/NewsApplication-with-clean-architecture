package com.codedev.newsapplication.domain.repositories

import androidx.paging.PagingData
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NewsRepository {
    suspend fun searchArticle(query: String) : Flow<PagingData<EntityArticle>>

    suspend fun trendingArticles() : Flow<PagingData<EntityArticle>>

    fun getAllArticles() : Flow<List<EntityArticle>>

    suspend fun saveArticle(article: EntityArticle)

    suspend fun getArticle(id: Int): EntityArticle

    suspend fun deleteArticle(article: EntityArticle)

    suspend fun searchCacheArticles(query: String): Flow<Resources<List<EntityArticle>>>

}