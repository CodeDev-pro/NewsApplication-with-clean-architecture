package com.codedev.newsapplication.domain.use_cases

data class NewsUseCases(
    val searchArticle: SearchArticle,
    val getTrendingHeadlines: GetTrendingHeadlines,
    val deleteArticle: DeleteArticle,
    val getAllCacheArticles: GetAllCacheArticles,
    val getCacheArticle: GetCacheArticle,
    val saveArticle: SaveArticle,
    val searchCacheArticle: SearchCacheArticle,
)
