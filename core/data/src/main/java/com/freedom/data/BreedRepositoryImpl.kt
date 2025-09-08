package com.freedom.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.freedom.common.NetworkResult
import com.freedom.data.mapper.toDomain
import com.freedom.data.paging.BreedPagingSource
import com.freedom.data.paging.SearchPagingSource
import com.freedom.model.CatModel
import com.freedom.network.NetworkDatasource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val networkDatasource: NetworkDatasource
) : BreedRepository {
    override fun getCatBreed(query: String, limit: Int): Flow<PagingData<CatModel>> {
        return Pager(
            config = PagingConfig(pageSize = limit, enablePlaceholders = false),
            pagingSourceFactory = {
                if (query.isBlank()) {
                    BreedPagingSource(networkDatasource = networkDatasource, limit = limit)
                } else {
                    SearchPagingSource(networkDatasource = networkDatasource, query = query)
                }
            }
        ).flow
    }
}

