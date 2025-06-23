package com.freedom.data
import com.freedom.common.NetworkResult
import com.freedom.model.CatModel

interface BreedRepository {
     suspend fun getCatBreed(): NetworkResult<CatModel>
}