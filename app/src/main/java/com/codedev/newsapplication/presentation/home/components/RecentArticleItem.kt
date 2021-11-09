package com.codedev.newsapplication.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codedev.newsapplication.R
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite

@Preview(showBackground = true)
@Composable
fun ArticleItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.4f)
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bitcoin),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp)
                ) {
                    Text(text = "world news 12h")
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "Australia to deport kiwi criminal who has lived there since he was 9",
                        style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(25.dp))
                                .background(Color.White.copy(alpha = 0.1f))
                                .clickable { }
                                .padding(3.dp)
                        ) {
                            Text(
                                text = "CNN",
                                style = MaterialTheme.typography.body1.copy(
                                    color = TextWhite,
                                    fontSize = 12.sp
                                ),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(25.dp))
                                .background(Color.White.copy(alpha = 0.1f))
                                .clickable { }
                                .padding(3.dp)
                        ) {
                            Text(
                                text = "Date",
                                style = MaterialTheme.typography.body1.copy(
                                    color = TextWhite,
                                    fontSize = 12.sp
                                ),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreHoriz,
                        contentDescription = null,
                        tint = TextWhite
                    )
                }
            }
            Row(
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = TextWhite
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Save", style = MaterialTheme.typography.body1)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(TextWhite)
                .height(0.5.dp))
        }
    }
}