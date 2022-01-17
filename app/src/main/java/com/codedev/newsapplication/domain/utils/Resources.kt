package com.codedev.newsapplication.domain.utils

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null,
    val isError: Boolean? = null
) {
    class Error<T>(isError: Boolean?, message: String?) :
        Resources<T>(isError = true, message = message)

    class Success<T>(data: T?, isError: Boolean?, message: String?) :
        Resources<T>(data = data, isError = isError, message = message)

    class Loading<T>() : Resources<T>()
}