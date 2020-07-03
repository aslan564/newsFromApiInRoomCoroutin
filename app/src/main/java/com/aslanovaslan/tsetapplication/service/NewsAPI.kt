package com.aslanovaslan.tsetapplication.service

import com.aslanovaslan.tsetapplication.newsModel.News
import com.aslanovaslan.tsetapplication.newsModel.NewsArticles
import retrofit2.Call
import retrofit2.http.GET
import java.lang.reflect.Type

interface NewsAPI {


    @GET("sources?language=en&apiKey=30f4de7ec5a34948b78b62dda745d3c7")
    fun getNewsData():Call<List<NewsArticles>>
}