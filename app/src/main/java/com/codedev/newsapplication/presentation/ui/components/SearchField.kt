package com.codedev.newsapplication.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codedev.newsapplication.R
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTint
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite

private const val TAG = "SearchField"

@Composable
fun SearchField(
    value: String = "",
    setValue: (String) -> Unit = {},
    editable: Boolean = true,
    onSearch: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.LightGray.copy(0.4f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = TextWhite.copy(0.5f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier.weight(1f),
            ) {
                BasicTextField(
                    value = value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        setValue(it)
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body1,
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearch(value)
                    }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    readOnly = !editable
                )
                if(value == "") {
                    Text(
                        text = "Search...",
                        style = MaterialTheme.typography.body1.copy(color = TextWhite.copy(0.5f)),
                    )
                }
            }
        }
    }
}