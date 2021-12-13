package com.codedev.newsapplication.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codedev.newsapplication.data.models.article_models.ArticleDTO
import javax.inject.Inject

class TrendingPagingSource @Inject constructor(
        private val api: NewsApi
    ) : PagingSource<Int, ArticleDTO>(){
        override fun getRefreshKey(state: PagingState<Int, ArticleDTO>): Int? {
            return state.anchorPosition?.let {
                state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
                state.closestPageToPosition(it)?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, ArticleDTO> {
            val page = params.key ?: DEFAULT_INIT_PAGE
            val data = api.getTrendingHeadlines(
                pageSize = params.loadSize,
                page = page
            )
            return when(data) {
                is Either.Right -> {
                    val articles = data.response.articles
                    LoadResult.Page(
                        data = articles,
                        prevKey = if(page == DEFAULT_INIT_PAGE) null else page - 1,
                        nextKey = if(articles.isEmpty()) null else page + 1
                    )
                }
                is Either.Left -> {
                    LoadResult.Error(data.error)
                }
            }
        }
    }