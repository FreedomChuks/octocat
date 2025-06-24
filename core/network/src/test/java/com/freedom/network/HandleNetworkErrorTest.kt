package com.freedom.network

import com.freedom.common.NetworkResult
import com.freedom.network.retrofit.convertErrorBody
import com.freedom.network.retrofit.handleNetworkError
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class HandleNetworkErrorTest {
    @Test
    fun `handleNetworkError maps IOException to Error with message`() {
        val io = IOException("network down")
        val error: NetworkResult<Any> = handleNetworkError(io)

        assertTrue(error is NetworkResult.Error)
        assertEquals("network down", (error as NetworkResult.Error).message)
    }

    @Test
    fun `handleNetworkError maps HttpException to Error with body`() {
        val responseBody = "error body".toResponseBody("text/plain".toMediaTypeOrNull())
        val response = Response.error<Any>(400, responseBody)
        val httpEx = HttpException(response)

        val error: NetworkResult<Any> = handleNetworkError(httpEx)

        assertTrue(error is NetworkResult.Error)
    }

    @Test
    fun `convertErrorBody returns null when body reading fails`() {
        val response = Response.error<Any>(500, byteArrayOf().toResponseBody(null))
        val httpEx = HttpException(response)

        val result = convertErrorBody(httpEx)
        assertEquals("null",result)
    }
}