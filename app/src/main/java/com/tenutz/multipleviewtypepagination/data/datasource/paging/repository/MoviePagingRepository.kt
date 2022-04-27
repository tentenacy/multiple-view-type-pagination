package com.tenutz.multipleviewtypepagination.data.datasource.paging.repository

import androidx.paging.PagingData
import com.tenutz.multipleviewtypepagination.data.datasource.api.dto.MoviesResponse
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Header
import retrofit2.http.Query

interface MoviePagingRepository {

    fun search(query: String): Flowable<PagingData<Movies.Movie>>
}