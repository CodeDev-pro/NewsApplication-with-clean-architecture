package com.codedev.newsapplication.di

import android.app.Application
import androidx.room.Room
import com.codedev.newsapplication.data.api.KtorClient
import com.codedev.newsapplication.data.api.NewsApi
import com.codedev.newsapplication.data.database.ArticleDatabase
import com.codedev.newsapplication.data.repositories.NewsRepositoryImpl
import com.codedev.newsapplication.domain.repositories.NewsRepository
import com.codedev.newsapplication.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient() = KtorClient.httpClient

    @Singleton
    @Provides
    fun provideNewsApi(client: HttpClient) = NewsApi(client)

    @Provides
    @Singleton
    fun provideArticleDatabase(application: Application): ArticleDatabase {
        return Room.databaseBuilder(
            application,
            ArticleDatabase::class.java,
            "article_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNewsRepository(api: NewsApi, db: ArticleDatabase): NewsRepository {
        return NewsRepositoryImpl(api, db.articleDao)
    }

    @Singleton
    @Provides
    fun provideNewsUseCases(repository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            searchArticle = SearchArticle(repository),
            getTrendingHeadlines = GetTrendingHeadlines(repository),
            deleteArticle = DeleteArticle(repository),
            saveArticle = SaveArticle(repository),
            getAllCacheArticles = GetAllCacheArticles(repository),
            searchCacheArticle = SearchCacheArticle(repository),
            getCacheArticle = GetCacheArticle(repository)
        )
    }
}