package com.codedev.newsapplication.di

import com.codedev.newsapplication.data.api.KtorClient
import com.codedev.newsapplication.data.api.NewsApi
import com.codedev.newsapplication.data.repositories.NewsRepositoryImpl
import com.codedev.newsapplication.domain.repositories.NewsRepository
import com.codedev.newsapplication.domain.use_cases.GetTrendingHeadlines
import com.codedev.newsapplication.domain.use_cases.NewsUseCases
import com.codedev.newsapplication.domain.use_cases.SearchArticle
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

    @Singleton
    @Provides
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideNewsUseCases(repository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            searchArticle = SearchArticle(repository),
            getTrendingHeadlines = GetTrendingHeadlines(repository)
        )
    }
}