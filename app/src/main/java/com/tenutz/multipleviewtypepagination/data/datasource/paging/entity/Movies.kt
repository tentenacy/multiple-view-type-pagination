package com.tenutz.multipleviewtypepagination.data.datasource.paging.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Movies(
    val total: Int = 0,
    val page: Int = 1,
    val movies: List<Movie>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total == page

    @Parcelize
    @Entity(tableName = "movies")
    data class Movie(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val title: String,
        val link: String,
        val image: String,
        val subtitle: String,
        val pubDate: String,
        val director: String,
        val actor: String,
        val userRating: String,
    ): Parcelable

    @Entity
    @Parcelize
    data class MovieRemoteKeys(
        @PrimaryKey val lastBuildDate: Long = 0,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}
