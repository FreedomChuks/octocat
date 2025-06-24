package com.freedom.data.mapper

import com.freedom.data.TestData
import com.freedom.network.model.CatApiResponse
import org.junit.Test

class CatMapperTest {
    @Test
    fun `toDomain maps all properties correctly`() {
        val catDto = CatApiResponse(
            id = "1",
            url = "https://example.com/cat.jpg",
            width = 100,
            height = 200,
            breeds = listOf(TestData.fakeBreedDto)
        )
        val cat = catDto.toDomain()

        assert(cat.id == catDto.id)
        assert(cat.url == catDto.url)
        assert(cat.width == catDto.width)
        assert(cat.height == catDto.height)
        assert(cat.breeds[0].id == catDto.breeds[0].id)
        assert(cat.breeds.isNotEmpty())
    }
}