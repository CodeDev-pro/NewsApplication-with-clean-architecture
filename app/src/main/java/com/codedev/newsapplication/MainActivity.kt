package com.codedev.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.codedev.newsapplication.presentation.favourites.FavouriteScreen
import com.codedev.newsapplication.presentation.home.HomeScreen
import com.codedev.newsapplication.presentation.search.SearchScreen
import com.codedev.newsapplication.presentation.settings.SettingsScreen
import com.codedev.newsapplication.presentation.ui.navigation.CustomBottomNavigation
import com.codedev.newsapplication.presentation.ui.navigation.CustomBottomNavigationScreens
import com.codedev.newsapplication.presentation.ui.theme.NewsApplicationTheme
import com.codedev.newsapplication.presentation.weather.WeatherScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    bottomBar = {
                        CustomBottomNavigation(
                            currentDestination = currentDestination,
                        ) {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = CustomBottomNavigationScreens.Home.route
                    ) {
                        composable(CustomBottomNavigationScreens.Home.route) {
                            HomeScreen()
                        }
                        composable(CustomBottomNavigationScreens.Favourite.route) {
                            FavouriteScreen()
                        }
                        composable(CustomBottomNavigationScreens.Search.route) {
                            SearchScreen()
                        }
                        composable(CustomBottomNavigationScreens.Weather.route) {
                            WeatherScreen()
                        }
                        composable(CustomBottomNavigationScreens.Settings.route) {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}