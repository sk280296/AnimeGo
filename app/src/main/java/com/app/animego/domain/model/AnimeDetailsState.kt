package com.app.animego.domain.model

import com.app.animego.data.dto.AnimeDetail

data class AnimeDetailsState(
    val loading: Boolean = false,
    var animeDetailResponse: AnimeDetail? = null,
    val error: String = ""
)