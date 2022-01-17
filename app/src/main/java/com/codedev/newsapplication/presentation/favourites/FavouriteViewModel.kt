package com.codedev.newsapplication.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codedev.newsapplication.domain.use_cases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val useCases: NewsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(FavouriteState())
    val state = _state.asStateFlow()

    val execute: (FavouriteEvents) -> Unit = { event ->
        viewModelScope.launch {
            when(event) {
                is FavouriteEvents.GetAllArticles -> {
                    useCases.getAllCacheArticles().collectLatest {
                        if(it.isEmpty()) {
                            _state.value = state.value.copy(
                                items = null,
                                loading = true
                            )
                        }
                        else {
                            _state.value = state.value.copy(
                                items = it,
                                loading = false
                            )
                        }
                    }
                }
            }
        }
    }

}