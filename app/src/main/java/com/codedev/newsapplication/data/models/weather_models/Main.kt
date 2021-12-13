package com.codedev.newsapplication.data.models.weather_models

import kotlinx.serialization.SerialName

data class Main(
    val feels_like: Int,
    @SerialName("grnd_level")
    val groundLevel: Int,
    val humidity: Int,
    val pressure: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    val temp: Int,
    val temp_max: Double,
    val temp_min: Double
)