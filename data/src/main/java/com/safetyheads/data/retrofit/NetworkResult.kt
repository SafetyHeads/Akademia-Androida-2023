package com.safetyheads.akademiaandroida.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()

    companion object {
        fun <T> success(data: T): NetworkResult<T> = Success(data)
        fun error(exception: Throwable): NetworkResult<Nothing> = Error(exception)
        fun loading(): NetworkResult<Nothing> = Loading
    }
}