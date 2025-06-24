package com.freedom.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedDto(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val indoor: Int,
    val lap: Int?=null,
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
    val weight: Weight,
    @SerialName("vetstreet_url") val vetStreetUrl: String?=null,
    @SerialName("vcahospitals_url") val vcaHospitalsUrl: String?=null,
    @SerialName("country_codes") val countryCodes: String,
    @SerialName("life_span") val lifeSpan: String,
    @SerialName("alt_names") val altNames: String?=null,
    @SerialName("affection_level") val affectionLevel: Int,
    @SerialName("child_friendly") val childFriendly: Int,
    @SerialName("dog_friendly") val dogFriendly: Int,
    @SerialName("energy_level") val energyLevel: Int,
    @SerialName("health_issues") val healthIssues: Int,
    @SerialName("shedding_level") val sheddingLevel: Int,
    @SerialName("social_needs") val socialNeeds: Int,
    @SerialName("stranger_friendly") val strangerFriendly: Int,
    @SerialName("suppressed_tail") val suppressedTail: Int,
    @SerialName("short_legs") val shortLegs: Int,
    @SerialName("wikipedia_url") val wikipediaUrl: String?=null,
    @SerialName("reference_image_id") val referenceImageId: String?=null,
)
@Serializable
data class Weight(
    val imperial: String,
    val metric: String
)

