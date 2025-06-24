package com.freedom.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.freedom.common.NetworkResult
import com.freedom.data.mapper.toDomain
import com.freedom.data.paging.BreedPagingSource
import com.freedom.model.CatModel
import com.freedom.network.NetworkDatasource
import com.freedom.network.retrofit.safeNetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val networkDatasource: NetworkDatasource
):BreedRepository{
    override fun getCatBreed(limit: Int): Flow<PagingData<CatModel>> {
        return Pager(
            config = PagingConfig(pageSize = limit, enablePlaceholders = false),
            pagingSourceFactory = { BreedPagingSource(networkDatasource = networkDatasource, limit = limit) }
        ).flow
    }
}

