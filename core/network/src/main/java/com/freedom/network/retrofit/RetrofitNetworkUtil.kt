package com.freedom.network.retrofit

import com.freedom.common.NetworkResult
import retrofit2.HttpException
import java.io.IOException

/**
 * Executes the provided [apiCall] and converts the response to the desired type via [dataMapper].
 * Any exception thrown is mapped to a [NetworkResult.Error] so callers never have to deal
 * with low-level networking errors directly.
 */
suspend inline fun <T, R> safeNetworkCall(
    crossinline apiCall: suspend () -> T,
    crossinline dataMapper: (T) -> R,
): NetworkResult<R> = runCatching { apiCall() }
    .fold(
        onSuccess = { response -> NetworkResult.Success(dataMapper(response)) },
        onFailure = ::handleNetworkError
    )

/**
 * Transforms a thrown [Throwable] into a [NetworkResult.Error] instance.
 */
fun <T> handleNetworkError(throwable: Throwable): NetworkResult<T> = when (throwable) {
    is IOException   -> NetworkResult.Error(throwable.message.orEmpty())
    is HttpException -> NetworkResult.Error(convertErrorBody(throwable).orEmpty())
    else             -> NetworkResult.Error(throwable.message.orEmpty())
}

/**
 * Attempts to read the error body of an [HttpException] as a raw string.
 */
fun convertErrorBody(exception: HttpException): String? =
    runCatching { exception.response()?.body().toString() }.getOrNull()