package com.freedom.network

import com.freedom.network.model.BreedDto
import com.freedom.network.model.CatApiResponse

interface NetworkDatasource {
    suspend fun getCatBreed(page: Int, limit: Int): List<CatApiResponse>
    suspend fun searchBreeds(query: String): List<BreedDto>
}