package com.codedev.newsapplication.data.models.weather_models

import java.io.Serializable

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
): Serializable