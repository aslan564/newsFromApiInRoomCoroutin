package com.aslanovaslan.tsetapplication.db

import androidx.room.*
import com.aslanovaslan.tsetapplication.newsModel.NewsArticles
import retrofit2.http.GET

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsArticles: NewsArticles)

    @Query("SELECT * FROM articles")
    fun getAllNews(): List<NewsArticles>

}