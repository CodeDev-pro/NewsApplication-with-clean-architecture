package com.codedev.newsapplication.domain.entities

import androidx.annotation.DrawableRes
import com.codedev.newsapplication.R

data class WeatherImage(
    val type: WeatherType,
    @DrawableRes val image: Int
)

enum class WeatherType {
    SPRING, FALL, SNOW, SUMMER
}

val springImages = listOf(
    R.drawable.spring_1,
    R.drawable.spring_2,
    R.drawable.spring_3,
    R.drawable.spring_4,
)