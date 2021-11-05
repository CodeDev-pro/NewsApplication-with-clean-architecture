package com.codedev.newsapplication.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.codedev.newsapplication.data.api.NewsApi
import com.codedev.newsapplication.data.api.NewsPagingSource
import com.codedev.newsapplication.data.models.toEntityArticle
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsApi: NewsApi
): NewsRepository {

    override suspend fun searchArticle(
        query: String,
        page: Int
    ): Flow<PagingData<EntityArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { NewsPagingSource(newsApi, query) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toEntityArticle()
            }
        }
    }

    /*override suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): Flow<PagingData<EntityArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { NewsPagingSource(newsApi, query) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toEntityArticle()
            }
        }
    }*/
}