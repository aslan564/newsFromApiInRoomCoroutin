package com.aslanovaslan.tsetapplication.newsModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
class NewsArticles {
    @PrimaryKey
    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    lateinit var url: String
    lateinit var category: String
    lateinit var language: String
    lateinit var country: String
}
