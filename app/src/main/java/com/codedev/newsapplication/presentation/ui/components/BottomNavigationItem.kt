package com.codedev.newsapplication.presentation.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codedev.newsapplication.presentation.ui.navigation.CustomBottomNavigationScreens
import com.codedev.newsapplication.presentation.ui.theme.LightBlue300
import com.codedev.newsapplication.presentation.ui.theme.LightGrayTint
import com.codedev.newsapplication.presentation.ui.theme.TextWhite
import kotlinx.coroutines.CoroutineScope

@Composable
fun CustomBottomNavigationItem(
    modifier: Modifier = Modifier,
    item: CustomBottomNavigationScreens,
    selected: Boolean = false,
    alwaysShowLabel: Boolean = false,
    onClick: (CustomBottomNavigationScreens) -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) item.color.copy(alpha = 0.3f) else Color.Transparent)
            .clickable {
                onClick(item)
            }
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painterResource(id = item.icon),
                contentDescription = item.title,
                tint = if (selected) item.color else TextWhite
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(if (selected) item.color.copy(alpha = 0.7f) else TextWhite.copy(0.3f))
                    .clip(
                        RoundedCornerShape(1.dp)
                    )
            )
        }
    }
}