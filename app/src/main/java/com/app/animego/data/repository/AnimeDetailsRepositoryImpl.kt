package com.app.animego.data.repository

import com.app.animego.data.ApiService
import com.app.animego.data.dto.AnimeDetail
import com.app.animego.data.dto.Characters
import com.app.animego.domain.repository.AnimeDetailsRepository
import com.app.animego.utils.Resource
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AnimeDetailsRepositoryImpl @Inject constructor(
    private val api: ApiService
) :
    AnimeDetailsRepository {
    override suspend fun getAnimeDetails(id: Int): Resource<AnimeDetail> {
        Resource.Loading(null)
        return try {
            val res = api.getAnimeDetails(id)
            if (res.isSuccessful) {
                res.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Unknown Error Occurred")
            } else
                Resource.Error("Unknown Error Occurred")
        } catch (e: HttpException) {
            Resource.Error("Unexpected HttpException " + e.localizedMessage)
        } catch (e: IOException) {
            Resource.Error("IO Exception, couldn't reach server " + e.localizedMessage)
        } catch (e: Exception) {
            Resource.Error("Unexpected Exception, couldn't reach server " + e.localizedMessage)
        }
    }

    override suspend fun getCastList(id: Int): Resource<Characters> {
        Resource.Loading(null)
        return try {
            val res = api.getCastList(id)
            if (res.isSuccessful) {
                res.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Unknown Error Occurred")
            } else
                Resource.Error("Unknown Error Occurred")
        } catch (e: HttpException) {
            Resource.Error("Unexpected HttpException " + e.localizedMessage)
        } catch (e: IOException) {
            Resource.Error("IO Exception, couldn't reach server " + e.localizedMessage)
        } catch (e: Exception) {
            Resource.Error("Unexpected Exception, couldn't reach server " + e.localizedMessage)
        }
    }

}