package com.tenutz.multipleviewtypepagination.data.datasource.api.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class MoviesResponse(
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
) {
    data class Item(
        val title: String,
        val link: String,
        val image: String,
        val subtitle: String,
        val pubDate: String,
        val director: String,
        val actor: String,
        val userRating: String,
    )
}
