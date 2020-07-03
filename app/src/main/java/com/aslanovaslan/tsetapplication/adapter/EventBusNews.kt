package com.aslanovaslan.tsetapplication.adapter

import com.aslanovaslan.tsetapplication.newsModel.NewsArticles

class EventBusNews {
    internal class SendNewsData(val newsArticles: NewsArticles,val color:String)
}