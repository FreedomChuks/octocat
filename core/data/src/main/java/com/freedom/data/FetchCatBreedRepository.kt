package com.freedom.data
import com.freedom.model.CatModel

interface FetchCatBreedRepository {
     suspend fun getCatBreed(): List<CatModel>
}