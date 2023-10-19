package com.example.got_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


class MainActivity : AppCompatActivity() {
    private var charImageURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.char_button)
        val imageView: ImageView = findViewById(R.id.char_image)

        button.setOnClickListener {
            getGotCharURL(imageView)
        }

        getGotCharURL(imageView)
    }

    private fun getGotCharURL(imageView: ImageView) {
        val client = AsyncHttpClient()
        val rand_num = (0..100).random()

        client["https://thronesapi.com/api/v2/Characters/$rand_num", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Char", "Request successful")
                println(json)

                val fullName = json.jsonObject.getString("fullName")
                val title =json.jsonObject.getString("title")
                charImageURL = json.jsonObject.getString("imageUrl")

                val tvCharInfoName = findViewById<TextView>(R.id.tv_char_info_name)
                val tvCharInfoTitle = findViewById<TextView>(R.id.tv_char_info_title)
                tvCharInfoName.text = "$fullName"
                tvCharInfoTitle.text = "Also known as: $title"

                loadImage(charImageURL, imageView)
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Error", errorResponse)
            }
        }]
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(imageView)
    }
}
