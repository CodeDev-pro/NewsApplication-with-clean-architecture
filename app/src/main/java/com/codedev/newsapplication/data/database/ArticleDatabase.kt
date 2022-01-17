package com.codedev.newsapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codedev.newsapplication.domain.entities.EntityArticle

@Database(entities = [EntityArticle::class], version = 1)
abstract class ArticleDatabase: RoomDatabase() {

    abstract val articleDao: ArticleDao
}