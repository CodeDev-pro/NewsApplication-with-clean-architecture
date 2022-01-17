package com.codedev.newsapplication.data.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.codedev.newsapplication.domain.entities.EntityArticle

@ExperimentalPagingApi
class ArticleRemoteMediator: RemoteMediator<Int, EntityArticle>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EntityArticle>
    ): MediatorResult {
        return MediatorResult.Error(Exception(""))
    }
}