package com.codedev.newsapplication.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.codedev.newsapplication.R
import com.codedev.newsapplication.presentation.ui.theme.*

sealed class CustomBottomNavigationScreens(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int,
    val color: Color
) {
    object Home : CustomBottomNavigationScreens(
        "home", "Home", R.drawable.ic_home, LightBlue300
    )

    object Favourite : CustomBottomNavigationScreens(
        "favourite", "Favourite", R.drawable.ic_bookmark, MidBlue
    )

    object Search : CustomBottomNavigationScreens(
        "search", "Search", R.drawable.ic_search, LightBlue300
    )

    object Weather : CustomBottomNavigationScreens(
        "weather", "Weather", R.drawable.ic_weather, ColorPurple
    )

    object Settings : CustomBottomNavigationScreens(
        "settings", "Settings", R.drawable.ic_settings, ColorPink
    )
}

val items = listOf(
    CustomBottomNavigationScreens.Home,
    CustomBottomNavigationScreens.Favourite,
    CustomBottomNavigationScreens.Search,
    CustomBottomNavigationScreens.Weather,
    CustomBottomNavigationScreens.Settings
)