package com.example.project2

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApiService {
    @GET("themeSearchList")
    fun getTouristPlaces(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("MobileOS") mobileOS: String,
        @Query("MobileApp") mobileApp: String,
        @Query("keyword") keyword: String,
        @Query("langCode") langCode: String,
        @Query("_type") type: String = "json"
    ): Call<ResponseBody>
}
