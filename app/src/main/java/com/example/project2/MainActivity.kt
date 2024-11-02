package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TouristPlaceAdapter
    private lateinit var editTextKeyword: EditText
    private lateinit var buttonSearch: Button
    private lateinit var textViewNoResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        editTextKeyword = findViewById(R.id.editTextKeyword)
        buttonSearch = findViewById(R.id.buttonSearch)
        textViewNoResults = findViewById(R.id.textViewNoResults)

        buttonSearch.setOnClickListener {
            val keyword = editTextKeyword.text.toString()
            if (keyword.isNotEmpty()) {
                fetchTouristPlaces(keyword)
            } else {
                Toast.makeText(this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchTouristPlaces(keyword: String) {
        val apiService = RetrofitClient.dataApiService
        val serviceKey = "7cYEU+/Ah49E0pkgxK6vA9+uEKouk8lCIClEh5QW1a849dwJSZPGyRVapudWPxU86R1S0F2vf3gH26JTn/0Nvw=="

        apiService.getTouristPlaces(
            numOfRows = 10,
            pageNo = 1,
            mobileOS = "ETC",
            mobileApp = "AppTest&_type=json",
            serviceKey = serviceKey,
            keyword = keyword,
            langCode = "ko",
            type = "json"
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val responseBodyString = response.body()?.string()
                        Log.d("MainActivity", "API Response Body: $responseBodyString")

                        if (responseBodyString != null && responseBodyString.trim().startsWith("{")) {
                            val gson = Gson()
                            val touristPlaceResponse = gson.fromJson(responseBodyString, TouristPlaceResponse::class.java)
                            val places = touristPlaceResponse.response.body.items.item ?: listOf()

                            if (places.isNotEmpty()) {
                                textViewNoResults.visibility = View.GONE
                                adapter = TouristPlaceAdapter(places) { place ->
                                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                    intent.putExtra("title", place.title)
                                    intent.putExtra("address", "${place.addr1} ${place.addr2}")
                                    intent.putExtra("imageUrl", place.imageUrl)
                                    startActivity(intent)
                                }
                                recyclerView.adapter = adapter
                            } else {
                                textViewNoResults.visibility = View.VISIBLE
                                adapter = TouristPlaceAdapter(emptyList()) {}
                                recyclerView.adapter = adapter
                            }
                        } else if (responseBodyString != null) {
                            if (responseBodyString.trim().startsWith("<")) {
                                // XML response handling
                                Log.e("MainActivity", "XML response: $responseBodyString")
                            } else {
                                Log.e("MainActivity", "Unexpected response: $responseBodyString")
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error parsing response", e)
                    }
                } else {
                    Log.e("MainActivity", "Failed response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("MainActivity", "API call failed", t)
            }
        })
    }
}

