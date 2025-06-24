package com.freedom.network.retrofit


import com.freedom.network.TestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class NetworkDataSourceImplTest {
    private val service: CatApiService = mockk()
    private lateinit var dataSource: NetworkDataSourceImpl

    @Before
    fun setUp() {
        dataSource = NetworkDataSourceImpl(service)
    }

    @Test
    fun `getCatBreed returns data from service`() = runTest {
        coEvery { service.getCatsWithBreeds(page = 2, limit = 5) } returns listOf(TestData.fakeCatImageDto)

        val result = dataSource.getCatBreed(page = 2, limit = 5)

        assertEquals(listOf(TestData.fakeCatImageDto), result)
        coVerify(exactly = 1) { service.getCatsWithBreeds(page = 2, limit = 5) }
    }

    @Test(expected = IOException::class)
    fun `getCatBreed throws IOException from service`() = runTest {
        coEvery { service.getCatsWithBreeds(any(), any()) } throws IOException("network down")
        dataSource.getCatBreed(page = 0, limit = 10)
    }

    @Test(expected = HttpException::class)
    fun `getCatBreed throws HttpException from service`() = runTest {

        val body = "Too many requests".toResponseBody("text/plain".toMediaTypeOrNull())

        val errorResponse: Response<String> = Response.error(429, body)

        coEvery { service.getCatsWithBreeds(any(), any()) } throws HttpException(errorResponse)

        dataSource.getCatBreed(page = 1, limit = 3)
    }

}