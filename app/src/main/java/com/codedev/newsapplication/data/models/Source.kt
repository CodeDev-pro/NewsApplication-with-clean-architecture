package com.codedev.newsapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String,
    val name: String
)