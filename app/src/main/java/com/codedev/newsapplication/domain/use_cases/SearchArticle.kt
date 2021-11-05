package com.codedev.newsapplication.domain.use_cases

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.domain.repositories.NewsRepository
import com.codedev.newsapplication.domain.utils.UiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchArticle constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(
        query: String,
        page: Int
    ): Flow<PagingData<UiModel>> {
        return repository.searchArticle(
            query = query,
            page = page
        ).map { pagingData ->
            pagingData.map {
                if(it.source == "cnn"){
                    UiModel.TrendingEntityArticle(it)
                } else {
                    UiModel.EntityArticleItem(it)
                }
            }
        }
    }
}