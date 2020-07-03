package com.aslanovaslan.tsetapplication.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslanovaslan.tsetapplication.R
import com.aslanovaslan.tsetapplication.adapter.EventBusNews
import com.aslanovaslan.tsetapplication.adapter.NewsAdapter
import com.aslanovaslan.tsetapplication.db.DatabaseNews
import com.aslanovaslan.tsetapplication.fragment.DetalisFragment
import com.aslanovaslan.tsetapplication.newsModel.News
import com.aslanovaslan.tsetapplication.newsModel.NewsArticles
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException


class MainActivity : AppCompatActivity(), NewsAdapter.onclikListener {

    private val url =
        "https://newsapi.org/v2/sources?language=en&apiKey=30f4de7ec5a34948b78b62dda745d3c7"
    private lateinit var newsFromApi: ArrayList<NewsArticles>
    private lateinit var newsAdapter: NewsAdapter

    private var myJob: Job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + myJob)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isMetered = cm.isActiveNetworkMetered
*/
        if (isNetworkAviable()){
            loadData()
            Toast.makeText(this, "internet var", Toast.LENGTH_SHORT).show()
        }else{
            uiScope.launch (Dispatchers.IO){
                val dataBaseInfo = DatabaseNews(this@MainActivity).getNewsDao().getAllNews()
                newsFromApi = ArrayList(dataBaseInfo)
                launch(Dispatchers.Main){
                    val layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewNewsArticles.layoutManager = layoutManager
                    newsAdapter = NewsAdapter(newsFromApi, this@MainActivity)
                    recyclerViewNewsArticles.adapter = newsAdapter
                }
            }
            Toast.makeText(this, "internet yoxdu", Toast.LENGTH_SHORT).show()
        }


    }
private fun isNetworkAviable():Boolean{
    val connectivityManager=getSystemService(Context.CONNECTIVITY_SERVICE)
   return if (connectivityManager is ConnectivityManager) {
        val networkInfo=connectivityManager.isActiveNetworkMetered
        networkInfo
    }else false

}
    private fun loadData() {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {

                    try {
                        val body = response.body?.string()
                        val gson = GsonBuilder().create()
                        val datas = gson.fromJson(body, News::class.java)
                        newsFromApi = ArrayList(datas.sources)

                        uiScope.launch {
                            val layoutManager = LinearLayoutManager(this@MainActivity)
                            recyclerViewNewsArticles.layoutManager = layoutManager
                            newsAdapter = NewsAdapter(newsFromApi, this@MainActivity)
                            recyclerViewNewsArticles.adapter = newsAdapter
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

        })

    }

  /*  fun saveDatabaseNews(): List<NewsArticles>? {
        var dataBaseInfo: List<NewsArticles>? = null
        uiScope.launch (Dispatchers.IO){
              dataBaseInfo = DatabaseNews(this@MainActivity).getNewsDao().getAllNews()
        }
        return  dataBaseInfo
    }*/
    private fun saveNewsDatabase(newsArticles: NewsArticles) {
        class SaveNewsDatabase : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                DatabaseNews(this@MainActivity).getNewsDao().insertNews(newsArticles)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(this@MainActivity, "News Saved To Database", Toast.LENGTH_SHORT)
                    .show()
                println("-----------------------------------------${result.toString()}")
            }
        }
        SaveNewsDatabase().execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        myJob.cancel()
    }

    /*override fun onClickListener(noinline: NewsArticles) {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isMetered = cm.isActiveNetworkMetered


    }*/

    override fun onBackPressed() {
        super.onBackPressed()
        containerFragmentDetalis.visibility = View.GONE
        linearLayout.visibility = View.VISIBLE
    }

    private fun nextFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragmentDetalis, DetalisFragment())
        transaction.addToBackStack("goDetalisNews")
        transaction.commit()
    }

    override fun onClickListener(noinline: NewsArticles, backColor: String) {
        if (isNetworkAviable()){
            saveNewsDatabase(noinline)
            containerFragmentDetalis.visibility = View.VISIBLE
            linearLayout.visibility = View.GONE
            nextFragment()
            EventBus.getDefault().postSticky(EventBusNews.SendNewsData(noinline,backColor))
            Toast.makeText(this, "internet var", Toast.LENGTH_SHORT).show()
        }else{
            containerFragmentDetalis.visibility = View.VISIBLE
            linearLayout.visibility = View.GONE
            nextFragment()
            EventBus.getDefault().postSticky(EventBusNews.SendNewsData(noinline,backColor))
            Toast.makeText(this, "internet yoxdu", Toast.LENGTH_SHORT).show()
        }
    }
}
