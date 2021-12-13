package com.codedev.newsapplication.data.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.codedev.newsapplication.data.api.NewsApi
import com.codedev.newsapplication.data.api.NewsPagingSource
import com.codedev.newsapplication.data.api.TrendingPagingSource
import com.codedev.newsapplication.data.models.article_models.toEntityArticle
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsApi: NewsApi
): NewsRepository {

    override suspend fun searchArticle(
        query: String
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

    override suspend fun trendingArticles(): Flow<PagingData<EntityArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { TrendingPagingSource(newsApi) }
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