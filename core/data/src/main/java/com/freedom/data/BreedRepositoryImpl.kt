package com.freedom.data

import com.freedom.data.mapper.toDomain
import com.freedom.network.NetworkDatasource
import com.freedom.network.retrofit.safeNetworkCall
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val networkDatasource: NetworkDatasource
):BreedRepository{

    override suspend fun getCatBreed() = safeNetworkCall(
        apiCall = { networkDatasource.getCatBreed()},
        dataMapper = { it.toDomain()}
    )
}