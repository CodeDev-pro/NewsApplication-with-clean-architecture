package com.codedev.newsapplication.presentation.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.codedev.newsapplication.R
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.presentation.ui.components.CustomChip
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite
import com.codedev.newsapplication.presentation.utils.CacheActions
import com.codedev.newsapplication.presentation.utils.isoToDateConverter

private const val TAG = "RecentArticleItem"

@ExperimentalCoilApi
@Composable
fun ArticleItem(
    textColor: Color = TextWhite,
    article: EntityArticle,
    onClick: (EntityArticle) -> Unit = {},
    onCacheAction: (CacheActions) -> Unit = {},
    onMoreClick: (EntityArticle) -> Unit = {},
    expandedItem: String = "",
    onDismiss: () -> Unit
) {
    val painter = rememberImagePainter(data = article.urlToImage, builder = {
        crossfade(true)
        error(R.drawable.ic_weather)
    })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(article)
            }
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
                    when (painter.state) {
                        is ImagePainter.State.Success -> {
                            Log.d(TAG, "ArticleItem: ${painter.state}")
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                        is ImagePainter.State.Loading -> {
                            Log.d(TAG, "ArticleItem: ${painter.state}")
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                        is ImagePainter.State.Error -> {
                            Log.d(TAG, "ArticleItem: ${painter.state}")
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "An unknown error occurred",
                                    style = MaterialTheme.typography.body1.copy(color = textColor)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                CustomChip(
                                    text = "Retry",
                                    onSelect = {

                                    }
                                )
                            }
                        }
                        else -> {
                            Log.d(TAG, "ArticleItem: ${painter.state}")
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "${article.source} • ${article.publishedAt.isoToDateConverter()}",
                        style = MaterialTheme.typography.caption.copy(color = textColor)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 20.sp,
                            color = textColor
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = article.description,
                        style = MaterialTheme.typography.caption.copy(
                            fontSize = 16.sp,
                            color = textColor
                        ),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(

                ) {
                    IconButton(onClick = { onMoreClick(article) }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreHoriz,
                            contentDescription = null,
                            tint = textColor
                        )
                    }
                    DropdownMenu(
                        expanded = expandedItem == article.url,
                        onDismissRequest = { onDismiss() },
                        modifier = Modifier.padding(end = 10.dp).align(Alignment.TopEnd)
                    ) {
                        DropdownMenuItem(onClick = { }) {
                            Text("Save")
                        }
                        DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                            Text("Delete")
                        }
                        Divider()
                        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                            Text("Share")
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (article.isSaved) onCacheAction(CacheActions.Delete(article))
                    else onCacheAction(CacheActions.Save(article))
                }) {
                    Icon(
                        imageVector = if (article.isSaved) Icons.Outlined.Bookmark else Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = TextWhite
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = if (article.isSaved) "Saved" else "Save",
                    style = MaterialTheme.typography.body1
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(textColor)
                    .height(0.5.dp)
            )
        }
    }
}

@Composable
fun TextItem(
    color: Color = Color.White,
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(color.copy(alpha = 0.1f))
            .clickable { }
            .padding(3.dp)
    ) {
        Text(
            text = "Date",
            style = MaterialTheme.typography.body1.copy(
                color = color,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(5.dp)
        )
    }
}