package com.freedom.network.retrofit

import com.freedom.network.NetworkDatasource
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val catApiService: CatApiService
): NetworkDatasource {
    override suspend fun getCatBreed(page: Int, limit: Int) = catApiService.getCatsWithBreeds(page = page, limit = limit)
}