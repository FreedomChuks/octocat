package com.freedom.network

import com.freedom.network.model.CatApiResponse

interface NetworkDatasource {
    suspend fun getCatBreeds(): List<CatApiResponse>
}