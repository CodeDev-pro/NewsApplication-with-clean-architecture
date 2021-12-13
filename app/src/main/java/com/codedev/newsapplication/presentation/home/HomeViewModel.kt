package com.codedev.newsapplication.presentation.home

import android.app.Application
import android.util.Log
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
        val DEFAULT_QUERY = "climate change"
        get() = field.trim()
    }

    private val _events = MutableSharedFlow<HomeEvents>(0)
    val events = _events.asSharedFlow()

    val actionFlow = MutableSharedFlow<HomeEvents>()
    val execute: (HomeEvents) -> Unit
    val pagingFlow : Flow<PagingData<UiModel>>

    val trendingPagingFlow : Flow<PagingData<UiModel>>

    val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()


    init {
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
                                    pagingData = state
                                )
                            }
                    }
                    is HomeEvents.GetTrendingArticles -> {
                        actionFlow.emit(it)
                        trendingPagingFlow.distinctUntilChanged()
                            .map { state ->
                                _state.value.copy(
                                    pagingData = state
                                )
                            }
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

}