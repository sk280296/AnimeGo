package com.app.animego.domain.usecase

import com.app.animego.data.dto.AnimeDetail
import com.app.animego.domain.repository.AnimeDetailsRepository
import com.app.animego.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class AnimeDetailsUseCase @Inject constructor(private val animeDetailsRepository: AnimeDetailsRepository) {
    operator fun invoke(id: Int): Flow<Resource<AnimeDetail>> =
        flow {

            emit(Resource.Loading())

            val data = animeDetailsRepository.getAnimeDetails(id)

            emit(data)

        }
}