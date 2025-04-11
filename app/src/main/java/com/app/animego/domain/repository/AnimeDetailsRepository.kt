package com.app.animego.domain.repository

import com.app.animego.data.dto.AnimeDetail
import com.app.animego.data.dto.Characters
import com.app.animego.utils.Resource

interface AnimeDetailsRepository {
    suspend fun getAnimeDetails(id: Int): Resource<AnimeDetail>

    suspend fun getCastList(id: Int): Resource<Characters>
}