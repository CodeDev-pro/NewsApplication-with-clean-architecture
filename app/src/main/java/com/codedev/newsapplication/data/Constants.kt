package com.codedev.newsapplication.data

const val WEATHER_API_KEY = "434050f69899eaf37069f8e2d2f8b472"
const val NEWS_API_KEY = "4fab21168bb2445c8b9797212e0b0e96"

const val GET_EVERYTHING = ""

fun getEverything(
    query: String,
    pageSize: Int,
    page: Int,
    apiKey: String = NEWS_API_KEY
): String {
    return "https://newsapi.org/v2/everything?q=$query&pageSize=$pageSize&page=$page&apiKey=$apiKey"
}

fun getTopHeadlines(
    category: String = "sports",
    country: String = "us",
    pageSize: Int,
    page: Int,
    apiKey: String = NEWS_API_KEY
): String {
    return "https://newsapi.org/v2/top-headlines?country=$country&pageSize=$pageSize&page=$page&category=$category&apiKey=$apiKey"
}