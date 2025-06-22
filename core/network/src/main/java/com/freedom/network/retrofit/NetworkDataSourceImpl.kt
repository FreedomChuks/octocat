package com.freedom.network.retrofit

import com.freedom.network.NetworkDatasource
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val catApiService: CatApiService
): NetworkDatasource {
    override suspend fun getCatBreeds() = catApiService.getCatsWithBreeds()
}