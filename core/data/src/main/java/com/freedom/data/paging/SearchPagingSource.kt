package com.freedom.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.freedom.data.mapper.toDomain
import com.freedom.model.CatModel
import com.freedom.network.NetworkDatasource
import com.freedom.network.model.CatApiResponse

class SearchPagingSource(
    private val networkDatasource: NetworkDatasource,
    private val query: String,
) : PagingSource<Int, CatModel>() {

    override fun getRefreshKey(state: PagingState<Int, CatModel>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatModel> {
        return try {
            val breeds = networkDatasource.searchBreeds(query)
            val catModels = breeds.mapNotNull { breed ->
                breed.referenceImageId?.let { imageId ->
                    CatApiResponse(
                        id = imageId,
                        url = "https://cdn2.thecatapi.com/images/$imageId.jpg",
                        breeds = listOf(breed),
                        width = 0,
                        height = 0
                    ).toDomain()
                }
            }
            LoadResult.Page(
                data = catModels,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
