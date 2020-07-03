package com.aslanovaslan.tsetapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslanovaslan.tsetapplication.R
import com.aslanovaslan.tsetapplication.newsModel.NewsArticles
import kotlinx.android.synthetic.main.single_city_info.view.*


class NewsAdapter(
    private val newsArticlesFromMainApi: ArrayList<NewsArticles>,
    private val listenerFromMain: onclikListener
) :
    RecyclerView.Adapter<NewsAdapter.NewsArticleHolder>() {
    interface onclikListener {
        fun onClickListener(noinline: NewsArticles,backColor:String)
    }
    private var colors:ArrayList<String> = arrayListOf("#87ceb0","#5aa7a1","#3c6a6d","#324765","#1d284f","#E6E6FA","#f4d9e4")

    class NewsArticleHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(newsArticles: ArrayList<NewsArticles>, position: Int,listener:onclikListener,color: ArrayList<String>) {
            itemView.tvNewsName.text = newsArticles[position].name
            itemView.setBackgroundColor(Color.parseColor(color[position%7]))
            itemView.tvFrNewsCategory.text=newsArticles[position].category
            itemView.setOnClickListener{
                listener.onClickListener(newsArticles[position],color[position%7])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_city_info, parent, false)

        return NewsArticleHolder(view)
    }

    override fun getItemCount(): Int {
        return newsArticlesFromMainApi.count()
    }

    override fun onBindViewHolder(holder: NewsArticleHolder, position: Int) {
        holder.bindItem(newsArticlesFromMainApi,position,listenerFromMain,colors)
    }
}