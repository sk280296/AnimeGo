package com.app.animego.data.repository

import com.app.animego.data.ApiService
import com.app.animego.data.dto.TopAnime
import com.app.animego.domain.repository.TopAnimeRepository
import com.app.animego.utils.Resource
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class TopAnimeRepositoryImpl @Inject constructor(
    private val api: ApiService,
) :
    TopAnimeRepository {
    override suspend fun getTopAnimes(): Resource<TopAnime> {
        return try {
            val res = api.getTopAnimes()
            if (res.isSuccessful) {
                res.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Unknown Error Occurred")
            } else
                Resource.Error("Unknown Error Occurred")
        } catch (e: HttpException) {
            Resource.Error("Unexpected HttpException " + e.localizedMessage)
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage)
        } catch (e: Exception) {
            Resource.Error("Unexpected Exception, couldn't reach server " + e.localizedMessage)
        }
    }

}