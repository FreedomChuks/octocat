package com.freedom.breed_list

import com.freedom.model.Breed
import com.freedom.model.CatModel
import com.freedom.model.Weight

object TestData {
    val fakeBreedDto = Breed(
        weight = Weight(imperial = "6 - 15", metric = "3 - 7"),
        id = "birm",
        name = "Birman",
        temperament = "Affectionate, Active, Gentle, Social",
        origin = "France",
        countryCodes = "FR",
        description = "Docile, quiet cat who loves people and will follow them from room to room…",
        lifeSpan = "14 - 15",
        indoor = 0,
        lap = 1,
        adaptability = 5,
        affectionLevel = 5,
        childFriendly = 4,
        dogFriendly = 5,
        energyLevel = 3,
        grooming = 2,
        healthIssues = 1,
        intelligence = 3,
        sheddingLevel = 3,
        socialNeeds = 4,
        strangerFriendly = 3,
        vocalisation = 1,
        experimental = 0,
        hairless = 0,
        natural = 0,
        rare = 0,
        rex = 0,
        suppressedTail = 0,
        shortLegs = 0,
        hypoallergenic = 0,
    )

    val fakeCatImageDto = CatModel(
        id      = "1",
        url     = "https://example.com/cat1.jpg",
        width   = 100,
        height  = 100,
        breeds  = listOf(fakeBreedDto)
    )

    fun fakeCatImageDtoPage(size: Int): List<CatModel> =
        List(size) { fakeCatImageDto.copy(id = "img${it + 1}") }
}