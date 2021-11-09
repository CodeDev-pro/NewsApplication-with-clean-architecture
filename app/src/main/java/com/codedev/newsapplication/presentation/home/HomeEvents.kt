package com.codedev.newsapplication.presentation.home

sealed class HomeEvents {
    data class Search(val query: String): HomeEvents()
}