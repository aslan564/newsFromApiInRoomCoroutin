package com.aslanovaslan.tsetapplication.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslanovaslan.tsetapplication.R
import com.aslanovaslan.tsetapplication.adapter.EventBusNews
import com.aslanovaslan.tsetapplication.newsModel.NewsArticles
import kotlinx.android.synthetic.main.fragment_detalis.view.*
import kotlinx.android.synthetic.main.single_city_info.view.tvFrNewsCategory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class DetalisFragment : Fragment() {
    private lateinit var dataNews: NewsArticles
    private lateinit var dataNewsColor: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalis, container, false)
        // Inflate the layout for this fragment
        //view.setBackgroundColor(Color.parseColor(dataNewsColor))
        view.tvFrNewsHeader.text = dataNews.name.toString()
        view.tvFrNewsCategory.text = dataNews.category.toString()
        view.tvFrLanguageNews.text = dataNews.language.toString()
        //view.tvFrLanguageNews.setBackgroundColor(Color.parseColor(dataNewsColor))
        view.tvFrNewsDescription.setText(dataNews.description)
        //view.tvFrNewsDescription.setBackgroundColor(Color.parseColor(dataNewsColor))
        view.tvFrNewsUrl.text = dataNews.url.toString()
        view.tvFrCountryName.text = dataNews.country.toString()
        return view
    }

    @Subscribe(sticky = true)
    internal fun getDataFromBusess(news: EventBusNews.SendNewsData) {
        dataNews = news.newsArticles
        dataNewsColor = news.color
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}