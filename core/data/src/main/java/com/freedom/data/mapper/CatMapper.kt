package com.freedom.data.mapper

import com.freedom.model.Breed
import com.freedom.model.CatModel
import com.freedom.network.model.BreedDto
import com.freedom.network.model.CatApiResponse

internal fun CatApiResponse.toDomain(): CatModel = CatModel(
    id = id,
    url = url,
    width = width,
    height = height,
    breeds = breeds.map(BreedDto::toDomain)
)

private fun BreedDto.toDomain(): Breed = Breed(
    id = id,
    name = name,
    temperament = temperament,
    origin = origin,
    description = description,
    indoor = indoor,
    lap = lap,
    adaptability = adaptability,
    grooming = grooming,
    intelligence = intelligence,
    vocalisation = vocalisation,
    experimental = experimental,
    hairless = hairless,
    natural = natural,
    rare = rare,
    rex = rex,
    hypoallergenic = hypoallergenic,
    vetStreetUrl = vetStreetUrl,
    vcaHospitalsUrl = vcaHospitalsUrl,
    countryCodes = countryCodes,
    lifeSpan = lifeSpan,
    altNames = altNames,
    affectionLevel = affectionLevel,
    childFriendly = childFriendly,
    dogFriendly = dogFriendly,
    energyLevel = energyLevel,
    healthIssues = healthIssues,
    sheddingLevel = sheddingLevel,
    socialNeeds = socialNeeds,
    strangerFriendly = strangerFriendly,
    suppressedTail = suppressedTail,
    shortLegs = shortLegs,
    wikipediaUrl = wikipediaUrl,
    referenceImageId = referenceImageId,
)