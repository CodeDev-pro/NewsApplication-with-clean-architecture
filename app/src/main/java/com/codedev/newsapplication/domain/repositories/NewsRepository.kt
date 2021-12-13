package com.codedev.newsapplication.domain.repositories

import androidx.paging.PagingData
import com.codedev.newsapplication.domain.entities.EntityArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NewsRepository {
    suspend fun searchArticle(query: String) : Flow<PagingData<EntityArticle>>
    suspend fun trendingArticles() : Flow<PagingData<EntityArticle>>

}