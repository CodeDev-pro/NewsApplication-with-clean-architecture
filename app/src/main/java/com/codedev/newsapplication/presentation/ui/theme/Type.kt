package com.codedev.newsapplication.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codedev.newsapplication.R

val LightOblique: FontWeight
    get() = FontWeight(weight = 450)
val BoldOblique: FontWeight
    get() = FontWeight(weight = 550)
val Dash: FontWeight
    get() = FontWeight(weight = 250)
val BookOblique: FontWeight
    get() = FontWeight(weight = 350)

val QuickSand = FontFamily(
    Font(R.font.quicksand_light_oblique, LightOblique),
    Font(R.font.quicksand_bold, FontWeight.Bold),
    Font(R.font.quicksand_bold_oblique, BoldOblique),
    Font(R.font.quicksand_dash, Dash),
    Font(R.font.quicksand_light, FontWeight.Light),
    Font(R.font.quicksand_book_oblique, BookOblique),
    Font(R.font.quicksand_book, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        color = TextWhite
    ),
    h1 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = TextWhite
    ),
    h6 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = BookOblique,
        fontSize = 20.sp,
        color = TextWhite
    ),
    h2 = TextStyle(
        fontFamily = QuickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        color = TextWhite
    ),
    caption = TextStyle(
        fontFamily = QuickSand,
        fontWeight = LightOblique,
        fontSize = 12.sp,
        color = TextWhite
    ),
)