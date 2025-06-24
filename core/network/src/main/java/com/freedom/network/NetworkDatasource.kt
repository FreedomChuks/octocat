package com.freedom.network

import com.freedom.network.model.CatApiResponse

interface NetworkDatasource {
    suspend fun getCatBreed(page: Int, limit: Int): List<CatApiResponse>
}