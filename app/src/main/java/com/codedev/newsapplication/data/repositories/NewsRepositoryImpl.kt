package com.codedev.newsapplication.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.codedev.newsapplication.data.api.NewsApi
import com.codedev.newsapplication.data.api.NewsPagingSource
import com.codedev.newsapplication.data.api.TrendingPagingSource
import com.codedev.newsapplication.data.database.ArticleDao
import com.codedev.newsapplication.data.models.article_models.toEntityArticle
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.repositories.NewsRepository
import com.codedev.newsapplication.domain.utils.Resources
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    val articleDao: ArticleDao
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

    override fun getAllArticles(): Flow<List<EntityArticle>> = articleDao.getAllArticles()

    override suspend fun saveArticle(article: EntityArticle) {
        articleDao.insert(article)
    }

    override suspend fun getArticle(id: Int): EntityArticle {
        return articleDao.getArticle(id)
    }

    override suspend fun deleteArticle(article: EntityArticle) {
        articleDao.deleteArticle(article)
    }

    override suspend fun searchCacheArticles(query: String): Flow<Resources<List<EntityArticle>>> = flow {
        emit(Resources.Loading())
        try{
            val data = articleDao.searchArticle(query)
            emit(Resources.Success(data = data, isError = false, null))
        }catch(e: Exception) {
            emit(Resources.Error<List<EntityArticle>>(isError = true, message = e.message))
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