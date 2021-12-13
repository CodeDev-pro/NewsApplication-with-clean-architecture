package com.codedev.newsapplication.presentation.ui.navigation

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.codedev.newsapplication.presentation.ui.components.CustomBottomNavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "CustomBottomNavigation"

@Composable
fun CustomBottomNavigation(
    currentDestination: String?,
    onClick: (CustomBottomNavigationScreens) -> Unit,
) {
    Log.d(TAG, "CustomBottomNavigation: ${currentDestination?.toString()}")
    Box(modifier = Modifier.padding(10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(Color.White.copy(0.2f)),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                items.forEach { item ->
                    CustomBottomNavigationItem(
                        modifier = Modifier
                            .padding(6.dp)
                            .weight(1f),
                        item = item,
                        selected = currentDestination == item.route,
                        alwaysShowLabel = false,
                        onClick = onClick
                    )
                }
            }
        }
    }
}