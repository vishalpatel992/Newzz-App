package com.example.newzzapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var recyclerView: (RecyclerView)= findViewById(R.id.recyclerView);

        recyclerView.layoutManager =
            LinearLayoutManager(this) // layout manager decides style of display//
        fetchData()
        mAdapter = NewsAdapter(this)

        recyclerView.adapter = mAdapter

    }
        private fun fetchData (){
            val url = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener {
                    val newsJsonArray = it.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for(i in 0 until newsJsonArray.length()){
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                            newsJsonObject.getString("title"),
                            newsJsonObject.getString("author"),
                            newsJsonObject.getString("url"),
                            newsJsonObject.getString("urlToImage"),

                            )

                        newsArray.add(news)
                    }

                    mAdapter.updateNews(newsArray)

                },
                Response.ErrorListener {

                }

            )

            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        }

        private fun load (){
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "https://www.google.com"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->

                },
                Response.ErrorListener {  })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }

        override fun onItemClicked(item: News) {


            val builder =  CustomTabsIntent.Builder();
            val customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(item.url))
        }
    }