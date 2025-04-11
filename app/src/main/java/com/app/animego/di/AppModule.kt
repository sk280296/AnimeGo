package com.app.animego.di

import android.content.Context
import com.app.animego.BuildConfig
import com.app.animego.data.ApiService
import com.app.animego.data.repository.AnimeDetailsRepositoryImpl
import com.app.animego.data.repository.TopAnimeRepositoryImpl
import com.app.animego.domain.repository.AnimeDetailsRepository
import com.app.animego.domain.repository.TopAnimeRepository
import com.app.animego.utils.Constants
import com.app.animego.utils.NetworkAvailabilityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTopAnimeRepository(
        api: ApiService
    ): TopAnimeRepository {
        return TopAnimeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAnimeDetailsRepository(
        api: ApiService
    ): AnimeDetailsRepository {
        return AnimeDetailsRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(NetworkAvailabilityInterceptor(context))
            .connectTimeout(Constants.API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Constants.API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}