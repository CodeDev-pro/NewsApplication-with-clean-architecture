package com.codedev.newsapplication.data.api

import android.util.Log
import com.codedev.newsapplication.data.getEverything
import com.codedev.newsapplication.data.getTopHeadlines
import com.codedev.newsapplication.data.models.article_models.NewsResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

private const val TAG = "NewsClient"

class NewsApi(
    private val client: HttpClient
) {

    suspend fun searchArticle(
        query: String,
        pageSize: Int,
        page: Int
    ): Either<Failure, NewsResponse> {
        return try {
            val url = getEverything(
                query = query,
                pageSize = pageSize,
                page = page
            )
            val response = client.request<NewsResponse>(url){
                method = HttpMethod.Get
            }
            Log.d(TAG, "searchArticle: ${response.toString()}")
            Either.Right(response)
        }catch (e: Exception) {
            Either.Left(e.catchExceptions())
        }
    }

    suspend fun getTrendingHeadlines(
        pageSize: Int,
        page: Int
    ): Either<Failure, NewsResponse> {
        return try {
            val url = getTopHeadlines(
                pageSize = pageSize,
                page = page
            )
            val response = client.request<NewsResponse>(url) {
                method = HttpMethod.Get
            }
            Either.Right(response)
        } catch (e: Exception) {
            Either.Left(e.catchExceptions())
        }
    }

}

sealed class Either<Error, Response> {
    data class Right<Error, Response>(val response: Response): Either<Error, Response>()
    data class Left<Error, Response>(val error: Error): Either<Error, Response>()
}

fun Exception.catchExceptions() = when(this) {
    is ServerResponseException -> Failure.HttpErrorInternalServerError(this)
    is ClientRequestException -> {
        when(this.response.status.value) {
            400 -> Failure.HttpErrorBadRequest(this)
            401 -> Failure.HttpErrorUnauthorized(this)
            403 -> Failure.HttpErrorForbidden(this)
            404 -> Failure.HttpErrorNotFound(this)
            else -> Failure.HttpError(this)
        }
    }
    is RedirectResponseException -> Failure.HttpError(this)
    else -> Failure.GenericError(this)
}

sealed class Failure(
    e: Exception
): Exception(e.message) {
    data class HttpErrorInternalServerError(val e: Exception): Failure(e)
    data class GenericError(val e: Exception): Failure(e)
    data class HttpErrorBadRequest(val e: Exception): Failure(e)
    data class HttpErrorUnauthorized(val e: Exception): Failure(e)
    data class HttpErrorForbidden(val e: Exception): Failure(e)
    data class HttpErrorNotFound(val e: Exception): Failure(e)
    data class HttpError(val e: Exception): Failure(e)
}