package com.freedom.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.freedom.common.NetworkResult
import com.freedom.data.mapper.toDomain
import com.freedom.model.CatModel
import com.freedom.network.NetworkDatasource
import com.freedom.network.retrofit.safeNetworkCall


class BreedPagingSource(
    private val networkDatasource: NetworkDatasource,
    private val limit: Int,
):PagingSource<Int, CatModel>() {

    override fun getRefreshKey(state: PagingState<Int, CatModel>): Int? =
        state.anchorPosition
            ?.let(state::closestPageToPosition)
            ?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatModel> {
        val page = params.key ?: 0
        val result = safeNetworkCall(
            apiCall = { networkDatasource.getCatBreed(page = page, limit = limit) },
            dataMapper = { dto -> dto.map { it.toDomain() } }
        )

        return when (result) {
            is NetworkResult.Success -> {
                val items = result.data.sortedBy { it.breedModels.first().name }
                val prev = (page - 1).takeIf { it >= 0 }
                val next = if (items.size < limit) null else page + 1
                LoadResult.Page(data = items, prevKey = prev, nextKey = next)
            }

            is NetworkResult.Error -> {
                LoadResult.Error(Throwable(result.message))
            }
        }

    }
}