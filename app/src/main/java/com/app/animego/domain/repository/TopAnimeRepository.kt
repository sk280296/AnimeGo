package com.app.animego.domain.repository

import com.app.animego.data.dto.TopAnime
import com.app.animego.utils.Resource

interface TopAnimeRepository {
    suspend fun getTopAnimes(): Resource<TopAnime>
}