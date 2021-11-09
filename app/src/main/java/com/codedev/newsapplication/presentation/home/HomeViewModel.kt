package com.codedev.newsapplication.presentation.home

import android.app.Application
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
        const val DEFAULT_QUERY = "climate change"
    }

    private val _events = MutableSharedFlow<HomeEvents>(0)
    val events = _events.asSharedFlow()

    val actionFlow = MutableSharedFlow<HomeEvents>()
    val execute: (HomeEvents) -> Unit
    val pagingFlow : Flow<PagingData<UiModel>>
    val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>


    init {
        pagingFlow = actionFlow
            .filterIsInstance<HomeEvents.Search>()
            .distinctUntilChanged()
            .onStart { emit(HomeEvents.Search("")) }
            .flatMapLatest { event ->
                _state.value = _state.value.copy(
                    query = event.query
                )
                searchVideo(event.query)
            }
        state = pagingFlow
            .distinctUntilChanged()
            .map { pagingData ->
                _state.value.copy(
                    pagingData = pagingData
                )
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                HomeState()
            )
        execute = {
            viewModelScope.launch { actionFlow.emit(it) }
        }
    }

    private suspend fun searchVideo(query: String) = useCases.searchArticle(query, 1)
        .cachedIn(viewModelScope)

}

sealed class UiEvents {

}