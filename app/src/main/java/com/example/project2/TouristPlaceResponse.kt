package com.example.project2

data class TouristPlaceResponse(
    val response: Response
)

data class Response(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)

data class Body(
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class Items(
    val item: List<TouristPlace>
)

data class TouristPlace(
    val tid: String,
    val title: String,
    val addr1: String,
    val addr2: String,
    val mapX: Double,
    val mapY: Double,
    val imageUrl: String,
    val createdtime: String,
    val modifiedtime: String
)
