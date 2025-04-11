package com.app.animego.domain.usecase

import com.app.animego.data.dto.TopAnime
import com.app.animego.utils.Resource
import com.app.animego.domain.repository.TopAnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class TopAnimeUseCase @Inject constructor(private val topAnimeRepository: TopAnimeRepository) {
    operator fun invoke(): Flow<Resource<TopAnime>> =
        flow {

            emit(Resource.Loading())

            val data = topAnimeRepository.getTopAnimes()

            emit(data)

        }
}