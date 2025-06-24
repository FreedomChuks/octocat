package com.freedom.network.di

import com.freedom.network.BuildConfig
import com.freedom.network.NetworkDatasource
import com.freedom.network.retrofit.CatApiService
import com.freedom.network.retrofit.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.thecatapi.com"
    private const val API_KEY = "live_tRoTpMfOKC2UePcT4fR8DdlMC1A6kLd2RP5ZTScDiUnIqNHjJ4rB2r74C3gZhtyt"
    private val CONTENT_TYPE = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request()
                    .newBuilder()
                    .addHeader("x-api-key", API_KEY)
                    .build()
                    .let(chain::proceed)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                },
            )
            .build()

    @Provides
    @Singleton
    fun provideCatApiService(
        json: Json,
        client: OkHttpClient,
    ): CatApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE))
            .build()
            .create(CatApiService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun bindNetworkDatasource(
        impl: NetworkDataSourceImpl,
    ): NetworkDatasource
}