package com.barys.fanficapp.data.vo

data class FanficDetails(
    val author: String,
    val avgRating: Double,
    val creationDate: String,
    val fandom: String,
    val genre: String,
    val id: Int,
    val name: String,
    val text: String,
    val picUrl: String,
    val quantityRatings: Int,
    val rating: Double
)