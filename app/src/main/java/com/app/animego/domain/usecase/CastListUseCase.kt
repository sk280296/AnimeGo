package com.app.animego.domain.usecase

import com.app.animego.data.dto.Characters
import com.app.animego.domain.repository.AnimeDetailsRepository
import com.app.animego.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class CastListUseCase @Inject constructor(private val movieDetailsRepository: AnimeDetailsRepository) {
    operator fun invoke(id: Int): Flow<Resource<Characters>> =
        flow {

            emit(Resource.Loading())

            val data = movieDetailsRepository.getCastList(id)

            emit(data)

        }
}