package com.codedev.newsapplication.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codedev.newsapplication.R
import com.codedev.newsapplication.presentation.ui.theme.TextWhite

@Composable
fun WeatherScreen(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {
        Image(
            painter = painterResource(id = R.drawable.snow_1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        WeatherBody()
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBody() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (titleRef, description, info) = createRefs()
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top, 10.dp)
                start.linkTo(parent.start, 10.dp)
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "New Zealand",
                    style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        tint = TextWhite
                    )
                }
            }
            Text(text = "9°", style = MaterialTheme.typography.h1)
        }

        Box(
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(parent.top, 100.dp)
                    end.linkTo(parent.end, 5.dp)
                }
                .rotate(-90f)

        ) {
            Text(
                text = "Clear Skies",
                style = MaterialTheme.typography.h6,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(info) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
                .padding(horizontal = 15.dp, vertical = 30.dp)
                .border(1.dp, TextWhite, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(TextWhite.copy(0.3f))
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                DescriptionInfo()
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp)
                        .background(TextWhite)
                )
                DescriptionInfo()
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp)
                        .background(TextWhite)
                )
                DescriptionInfo()
            }
        }
    }
}

@Composable
fun DescriptionInfo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "64%", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "64%", style = MaterialTheme.typography.body1.copy(fontSize = 15.sp))
    }
}