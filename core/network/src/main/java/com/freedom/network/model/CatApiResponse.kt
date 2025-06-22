package com.freedom.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CatApiResponse(
    val breeds: List<BreedDto>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
