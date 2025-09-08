package com.freedom.network.retrofit

import com.freedom.network.model.CatApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getCatsWithBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("has_breeds") hasBreeds: Int = 1,
    ): List<CatApiResponse>

    @GET("v1/breeds/search")
    suspend fun searchBreeds(@Query("q") query: String): List<BreedDto>
}