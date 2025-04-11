package com.app.animego.domain.model

import com.app.animego.data.dto.TopAnime

data class TopAnimeListState(
    val loading: Boolean = false,
    var topAnimeResponse: TopAnime? = null,
    val error: String = ""
)