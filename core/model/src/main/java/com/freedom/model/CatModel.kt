package com.freedom.model

data class CatModel(
    val breeds: List<Breed>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
data class Breed(
    val id: String,
    val weight: Weight,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val indoor: Int,
    val lap: Int?,
    val adaptability: Int,
    val grooming: Int,
    val intelligence: Int,
    val vocalisation: Int,
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    val hypoallergenic: Int,
    val vetStreetUrl: String?,
    val vcaHospitalsUrl: String?,
    val countryCodes: String,
    val lifeSpan: String,
    val altNames: String?,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val healthIssues: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val suppressedTail: Int,
    val shortLegs: Int,
    val wikipediaUrl: String?,
    val referenceImageId: String?,
)
data class Weight(
    val imperial: String,
    val metric: String
)
