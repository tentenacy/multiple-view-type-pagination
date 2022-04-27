package com.tenutz.multipleviewtypepagination.data.datasource.paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.tenutz.multipleviewtypepagination.data.datasource.api.dto.MoviesResponse
import com.tenutz.multipleviewtypepagination.data.datasource.api.remote.MovieSearchApi
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import com.tenutz.multipleviewtypepagination.data.datasource.paging.source.MovieRxPagingSource
import com.tenutz.multipleviewtypepagination.utils.mapper.MovieMapper
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviePagingRepositoryImpl @Inject constructor(
    private val movieSearchApi: MovieSearchApi,
    private val mapper: MovieMapper
): MoviePagingRepository {
    override fun search(query: String): Flowable<PagingData<Movies.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 20,
            ),
            pagingSourceFactory = { MovieRxPagingSource(movieSearchApi, mapper, query) }
        ).flowable
    }
}