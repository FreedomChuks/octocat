package com.freedom.network.retrofit

import com.freedom.network.model.CatApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NetworkDataSourceImplTest {
    private val service: CatApiService = mockk()
    private lateinit var dataSource: NetworkDataSourceImpl

    @Before
    fun setUp() {
        dataSource = NetworkDataSourceImpl(service)
    }

    @Test
    fun `getCatBreed returns response from service`() = runTest {
        val expected = CatApiResponse(
            breeds = emptyList(),
            id = "1",
            url = "https://example.com/cat.jpg",
            width = 100,
            height = 100
        )
        coEvery { service.getCatsWithBreeds(limit = 10, hasBreeds = 1) } returns expected

        val actual = dataSource.getCatBreed()

        assertEquals(expected, actual)
    }

}