package com.codedev.newsapplication.presentation.home

import android.app.Application
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.codedev.newsapplication.domain.use_cases.NewsUseCases
import com.codedev.newsapplication.domain.utils.UiModel
import com.codedev.newsapplication.presentation.utils.InternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: NewsUseCases,
    application: Application,
    private val savedStateHandle: SavedStateHandle
): AndroidViewModel(application) {

    val connection = InternetConnection(
        application,
        viewModelScope
    ).connection

    companion object {
        val DEFAULT_QUERY = queries[1].value
        get() = field.trim()
    }

    private val _events = MutableSharedFlow<UiEvents>(0)
    val events = _events.asSharedFlow()

    val actionFlow = MutableSharedFlow<HomeEvents>()
    val execute: (HomeEvents) -> Unit
    val pagingFlow : Flow<PagingData<UiModel>>

    val trendingPagingFlow : Flow<PagingData<UiModel>>

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()


    init {
        Log.d(TAG, "init: called")
        pagingFlow = actionFlow
            .filterIsInstance<HomeEvents.Search>()
            .distinctUntilChanged()
            .onStart { emit(HomeEvents.Search(DEFAULT_QUERY)) }
            .flatMapLatest { event ->
                _state.value = _state.value.copy(
                    query = event.query
                )
                searchVideo(event.query)
            }

        trendingPagingFlow = actionFlow
            .filterIsInstance<HomeEvents.GetTrendingArticles>()
            .distinctUntilChanged()
            .onStart { emit(HomeEvents.GetTrendingArticles) }
            .flatMapLatest { event ->
                getTrendingHeadlines()
            }
        execute = {
            viewModelScope.launch {
                when(it) {
                    is HomeEvents.Search -> {
                        actionFlow.emit(it)
                        pagingFlow.distinctUntilChanged()
                            .map { state ->
                                _state.value.copy(
                                    popularData = _state.value.popularData.copy(pagingData = state)
                                )
                            }
                    }
                    is HomeEvents.GetTrendingArticles -> {
                        actionFlow.emit(it)
                        trendingPagingFlow.distinctUntilChanged()
                            .map { state ->
                                _state.value.copy(
                                    trendingData = _state.value.trendingData.copy(pagingData = state)
                                )
                            }
                    }
                    is HomeEvents.PopularArticleScroll -> {
                        _state.value = state.value.copy(
                            popularData = state.value.popularData.copy(offset = it.offset, position = it.position)
                        )
                    }
                    is HomeEvents.TrendingArticleScroll -> {
                        _state.value = state.value.copy(
                            trendingData = state.value.trendingData.copy(offset = it.offset, position = it.position)
                        )
                    }
                    is HomeEvents.DeleteArticle -> {
                        useCases.deleteArticle(it.article) { isSuccess, error ->
                            if(!isSuccess && error != null) {
                                _events.emit(UiEvents.CacheMessage(error))
                            } else {
                                _events.emit(UiEvents.CacheMessage("Article Deleted Successfully"))
                            }
                        }
                    }
                    is HomeEvents.SaveArticle -> {
                        useCases.saveArticle(it.article) { isSuccess, error ->
                            if(!isSuccess && error != null) {
                                _events.emit(UiEvents.CacheMessage(error))
                            } else {
                                _events.emit(UiEvents.CacheMessage("Article Deleted Successfully"))
                            }
                        }
                    }
                    is HomeEvents.ShareArticle -> {

                    }
                }
            }
        }
    }

    private suspend fun searchVideo(query: String) = useCases.searchArticle(query)
        .cachedIn(viewModelScope)

    private suspend fun getTrendingHeadlines() = useCases.getTrendingHeadlines()
        .cachedIn(viewModelScope)

}

sealed class UiEvents {
    data class CacheMessage(val message: String): UiEvents()
}