package com.codedev.newsapplication.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codedev.newsapplication.data.models.article_models.ArticleDTO
import javax.inject.Inject

const val DEFAULT_INIT_PAGE = 1

class NewsPagingSource @Inject constructor(
    private val api: NewsApi,
    private val query: String
) : PagingSource<Int, ArticleDTO>(){
    override fun getRefreshKey(state: PagingState<Int, ArticleDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDTO> {
        val page = params.key ?: DEFAULT_INIT_PAGE
        val data = api.searchArticle(
            query = query,
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