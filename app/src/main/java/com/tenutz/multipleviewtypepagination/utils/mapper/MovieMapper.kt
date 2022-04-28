package com.tenutz.multipleviewtypepagination.utils.mapper

import com.tenutz.multipleviewtypepagination.data.datasource.api.dto.MoviesResponse
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.ceil
import kotlin.math.floor

@Singleton
class MovieMapper @Inject constructor() {

    fun transform(response: MoviesResponse): Movies {
        return with(response) {
            val calcTotal = floor(total.div(response.display.toFloat())).toInt()
            Movies(
                total = calcTotal,
                page = start,
                movies = items.map {
                    Movies.Movie(
                        0,
                        it.title,
                        it.link,
                        it.image,
                        it.subtitle,
                        it.pubDate,
                        it.director,
                        it.actor,
                        it.userRating,
                    )
                }
            )
        }
    }
}