package com.freedom.data

import com.freedom.common.NetworkResult
import com.freedom.network.NetworkDatasource
import com.freedom.network.model.BreedDto
import com.freedom.network.model.CatApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class BreedRepositoryImplTest {
    private val network:NetworkDatasource = mockk()
    private lateinit var repository: BreedRepositoryImpl

    @Before
    fun setUp() {
        repository = BreedRepositoryImpl(network)
    }

    @Test
    fun `getBreeds should return a list of breeds when API call is successful`() = runTest {
        //Given
        val breed = listOf(
            BreedDto(
                id = "1",
                name = "Breed1",
                temperament = "Temperament1",
                origin = "Origin1",
                description = "Description1",
                lifeSpan = "LifeSpan1",
                adaptability = 1,
                affectionLevel = 2,
                childFriendly = 3,
                dogFriendly = 4,
                energyLevel = 5,
                grooming = 1,
                wikipediaUrl = "WikipediaUrl1",
                vocalisation = 1,
                intelligence = 1,
                vetStreetUrl = "VetStreetUrl1",
                healthIssues = 1,
                socialNeeds = 1,
                strangerFriendly = 1,
                referenceImageId = "ReferenceImageId1",
                altNames = "AltNames1",
                indoor = 1,
                lap = 1,
                hairless = 1,
                rex = 1,
                rare = 2,
                hypoallergenic = 2,
                countryCodes = "US",
                experimental = 1,
                natural = 1,
                suppressedTail = 1,
                shortLegs = 1,
                sheddingLevel = 1,
                vcaHospitalsUrl = "VcaHospitalsUrl1"
            )
        )
        val dto = CatApiResponse(
            breeds = breed,
            url = "Url1",
            width = 100,
            height = 100,
            id = "1"
        )

        coEvery { network.getCatBreed() } returns dto

        val result = repository.getCatBreed()

        assertTrue(result is NetworkResult.Success)
        val successResult = (result as NetworkResult.Success).data
        assertTrue(successResult.breeds.isNotEmpty())
        assertTrue(successResult.breeds.size == 1)
        assertTrue(successResult.breeds[0].id == "1")
        assertTrue(successResult.breeds[0].name == "Breed1")
    }

    @Test
    fun `getBreeds should return an error when API call throws an IOException`() = runTest {
        coEvery { network.getCatBreed() } throws IOException("Network error")

        val result = repository.getCatBreed()

        assertTrue(result is NetworkResult.Error)
        val errorResult = (result as NetworkResult.Error).message
        assertTrue(errorResult == "Network error")
    }

}