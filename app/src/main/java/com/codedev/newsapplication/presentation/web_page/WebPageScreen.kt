package com.codedev.newsapplication.presentation.web_page

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.codedev.newsapplication.domain.entities.EntityArticle
import com.codedev.newsapplication.presentation.ui.components.SearchField
import com.codedev.newsapplication.presentation.ui.theme.DarkGrayTone
import com.codedev.newsapplication.presentation.ui.theme.TextWhite

@Composable
fun WebPageScreen(
    article: EntityArticle,
    viewModel: WebPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayTone)
    ) {
        Column {
            SearchField(
                value = article.url,
                setValue = {

                },
                editable = false
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TextWhite.copy(0.3f))
            )
            AndroidView(factory = {
                val webView = WebView(context).apply {
                    webViewClient = CustomWebViewClient(
                        isLoading,
                        setIsLoading = {
                            isLoading = it
                        }
                    )
                    loadUrl(article.url)
                }
                webView
            }, modifier = Modifier.fillMaxSize())
        }
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 58.dp, end = 10.dp)){
            FloatingActionButton(onClick = {  }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, tint = TextWhite)
            }
        }
        Box(modifier = Modifier
            .align(Alignment.Center)){
            if(isLoading) {
                CircularProgressIndicator()
            }

        }
    }
}

private class CustomWebViewClient constructor(
    private val isLoading: Boolean,
    private val setIsLoading: (Boolean) -> Unit
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        setIsLoading(true)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if(isLoading) {
            setIsLoading(false)
        }
    }
}

