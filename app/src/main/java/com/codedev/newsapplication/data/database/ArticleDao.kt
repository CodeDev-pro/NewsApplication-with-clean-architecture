package com.codedev.newsapplication.data.database

import androidx.room.*
import com.codedev.newsapplication.domain.entities.EntityArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: EntityArticle)

    @Query("SELECT * FROM entityarticle")
    fun getAllArticles(): Flow<List<EntityArticle>>

    @Query("SELECT * FROM entityarticle WHERE title LIKE :title")
    suspend fun searchArticle(title: String): List<EntityArticle>

    @Query("SELECT * FROM entityarticle WHERE id = :id")
    suspend fun getArticle(id: Int): EntityArticle

    @Delete
    suspend fun deleteArticle(article: EntityArticle)

}