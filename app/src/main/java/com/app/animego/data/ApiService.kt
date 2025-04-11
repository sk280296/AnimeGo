package com.app.animego.data

import com.app.animego.data.dto.AnimeDetail
import com.app.animego.data.dto.Characters
import com.app.animego.data.dto.TopAnime
import com.app.animego.utils.UrlConstants;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path

interface ApiService {
    @GET(UrlConstants.TOP_ANIME)
    suspend fun getTopAnimes(): Response<TopAnime>

    @GET(UrlConstants.CHARACTER_LIST)
    suspend fun getCastList(@Path("anime_id") animeId: Int): Response<Characters>

    @GET(UrlConstants.ANIME_DETAILS )
    suspend fun getAnimeDetails(@Path("anime_id") animeId: Int): Response<AnimeDetail>
}