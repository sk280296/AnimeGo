package com.app.animego.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.animego.domain.model.TopAnimeListState
import com.app.animego.domain.usecase.TopAnimeUseCase
import com.app.animego.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TopAnimeViewModel @Inject constructor(
    private val topAnimeUseCase: TopAnimeUseCase
) : ViewModel() {

    private val _topAnimeListState = MutableStateFlow(TopAnimeListState())
    val topAnimeListState: StateFlow<TopAnimeListState> = _topAnimeListState

    fun searchMovieList() {
        topAnimeUseCase().onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    _topAnimeListState.value = TopAnimeListState(loading = true)
                }

                is Resource.Success -> {
                    _topAnimeListState.value =
                        TopAnimeListState(topAnimeResponse = response.data)
                }

                is Resource.Error -> {
                    _topAnimeListState.value =
                        TopAnimeListState(error = response.message ?: "Unexpected Error happen")

                }
            }
        }.launchIn(viewModelScope)
    }

}