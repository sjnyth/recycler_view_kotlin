package com.example.got_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private val characters: MutableList<GoTCharacter> = mutableListOf()
    private lateinit var adapter: GoTCharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = GoTCharacterAdapter(characters)
        val rvCharacters = findViewById<RecyclerView>(R.id.rv_characters)
        rvCharacters.layoutManager = LinearLayoutManager(this)
        rvCharacters.adapter = adapter

        rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= lastVisibleItem + 2) { // To load more when reaching 2 items before the end
                    getGotCharURL()
                }
            }
        })

        // Populating the initial list
        for (i in 1..50) { // Request 50 characters initially
            getGotCharURL()
        }
    }

    private fun getGotCharURL() {
        val client = AsyncHttpClient()
        val rand_num = (0..100).random()

        client["https://thronesapi.com/api/v2/Characters/$rand_num", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Char", "Request successful")
                println(json)

                val fullName = json.jsonObject.getString("fullName")
                val title = json.jsonObject.getString("title")
                val imageUrl = json.jsonObject.getString("imageUrl")

                val character = GoTCharacter(fullName, title, imageUrl)
                characters.add(character)
                adapter.notifyItemInserted(characters.size - 1) // Notify adapter about the new item
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Error", errorResponse)
            }
        }]
    }
}
