package com.freedom.data.mapper

import com.freedom.model.BreedModel
import com.freedom.model.CatModel
import com.freedom.model.Weight
import com.freedom.network.model.BreedDto
import com.freedom.network.model.CatApiResponse

internal fun CatApiResponse.toDomain(): CatModel = CatModel(
    id = id,
    url = url,
    width = width,
    height = height,
    breedModels = breeds.map(BreedDto::toDomain)
)

private fun BreedDto.toDomain(): BreedModel = BreedModel(
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
    countryCodes = countryCodes,
    lifeSpan = lifeSpan,
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
    weight = Weight(
        imperial = weight.imperial,
        metric = weight.metric
    )
)