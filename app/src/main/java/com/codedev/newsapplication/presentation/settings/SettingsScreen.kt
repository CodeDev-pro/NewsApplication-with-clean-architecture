package com.codedev.newsapplication.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.copy(0.95f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            SettingsTitleSection()
            CountryTextField()
            SettingsButton(color)
        }
    }
}

@Composable
private fun CountryTextField() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(0.3f))
            .padding(10.dp),
    ) {
        BasicTextField(
            value = "",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            singleLine = true,
            textStyle = MaterialTheme.typography.body1.copy(DarkGrayTone),
            keyboardActions = KeyboardActions(onSearch = {}),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        Text(
            text = "Choose your Country..., Current Country: us",
            style = MaterialTheme.typography.body1.copy(color = TextWhite.copy(0.5f)),
        )
    }
}

@Composable
fun SettingsTitleSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.h1)
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@ExperimentalMaterialApi
@Composable
fun SettingsButton(color: Color) {
    Surface(
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        color = color,
        modifier = Modifier
            .clickable { }
            .padding(10.dp),
        onClick = {  }
    ) {
        Text(
            text = "Apply",
            style = MaterialTheme.typography.h2.copy(fontSize = 20.sp),
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp)
        )
    }
}