package com.codedev.newsapplication.domain.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import com.codedev.newsapplication.domain.repositories.NewsRepository
import com.codedev.newsapplication.domain.utils.UiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingHeadlines @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke() : Flow<PagingData<UiModel>> {
        return repository.trendingArticles().map { pagingData ->
            pagingData.map {
                UiModel.TrendingEntityArticle(it)
            }
        }
    }
}
