package com.freedom.data
import androidx.paging.PagingData
import com.freedom.model.CatModel
import kotlinx.coroutines.flow.Flow

interface BreedRepository {
     fun getCatBreed(query: String, limit: Int = 10): Flow<PagingData<CatModel>>
}