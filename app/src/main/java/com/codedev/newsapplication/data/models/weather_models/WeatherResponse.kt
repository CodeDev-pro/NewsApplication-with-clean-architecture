package com.codedev.newsapplication.data.models.weather_models

import kotlinx.serialization.SerialName
import java.io.Serializable

data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    @SerialName("coord")
    val coordinates: Coordinates,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
): Serializable