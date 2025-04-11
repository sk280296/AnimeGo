package com.app.animego.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.animego.domain.model.AnimeDetailsState
import com.app.animego.domain.model.CastListState
import com.app.animego.domain.usecase.AnimeDetailsUseCase
import com.app.animego.domain.usecase.CastListUseCase
import com.app.animego.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val animeDetailsUseCase: AnimeDetailsUseCase,
    private val castListUseCase: CastListUseCase
) : ViewModel() {

    private val _animeDetailsListState = MutableStateFlow(AnimeDetailsState())
    val animeDetailsListState: StateFlow<AnimeDetailsState> = _animeDetailsListState

    private val _castListState = MutableStateFlow(CastListState())
    val castListState: StateFlow<CastListState> = _castListState

    fun getAnimeDetails(id: Int) {
        animeDetailsUseCase(id).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    _animeDetailsListState.value = AnimeDetailsState(loading = true)
                }

                is Resource.Success -> {
                    _animeDetailsListState.value =
                        AnimeDetailsState(animeDetailResponse = response.data)
                }

                is Resource.Error -> {
                    _animeDetailsListState.value =
                        AnimeDetailsState(error = response.message ?: "Unexpected Error happen")

                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCastList(id: Int) {
        castListUseCase(id).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    _castListState.value = CastListState(loading = true)
                }

                is Resource.Success -> {
                    _castListState.value =
                        CastListState(castList = response.data)
                }

                is Resource.Error -> {
                    _castListState.value =
                        CastListState(error = response.message ?: "Unexpected Error happen")

                }
            }
        }.launchIn(viewModelScope)
    }

}