package com.freedom.network

import com.freedom.common.NetworkResult
import com.freedom.network.retrofit.safeNetworkCall
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class SafeNetworkCallTest {
    @Test
    fun `safeNetworkCall returns Success when apiCall succeeds`() = runTest {
        val dto = "data"
        val result = safeNetworkCall(apiCall = { dto }, dataMapper = { it.length })

        assertTrue(result is NetworkResult.Success)
        assertEquals(4, (result as NetworkResult.Success).data)
    }

    @Test
    fun `safeNetworkCall returns Error when apiCall throws IOException`() = runTest {
        val io = IOException("fail")
        val result = safeNetworkCall<String, Int>(apiCall = { throw io }, dataMapper = { it.length })

        assertTrue(result is NetworkResult.Error)
        assertEquals("fail", (result as NetworkResult.Error).message)
    }

    @Test
    fun `singleShotNetworkCall returns Error when apiCall throws HttpException`() = runTest {
        val response = mockk<retrofit2.Response<Any>>(relaxed = true)
        val httpEx = HttpException(response)
        val result = safeNetworkCall<String, Int>(apiCall = { throw httpEx }, dataMapper = { it.length })

        assertTrue(result is NetworkResult.Error)
    }
}