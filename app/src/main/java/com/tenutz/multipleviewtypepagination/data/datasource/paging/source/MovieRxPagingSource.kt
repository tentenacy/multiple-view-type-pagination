package com.tenutz.multipleviewtypepagination.data.datasource.paging.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.tenutz.multipleviewtypepagination.data.datasource.api.remote.MovieSearchApi
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import com.tenutz.multipleviewtypepagination.utils.applyRetryPolicy
import com.tenutz.multipleviewtypepagination.utils.mapper.MovieMapper
import com.tenutz.multipleviewtypepagination.utils.costant.policy.RetryPolicyConstant
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MovieRxPagingSource(
    private val movieSearchApi: MovieSearchApi,
    private val mapper: MovieMapper,
    private val query: String,
): RxPagingSource<Int, Movies.Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movies.Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movies.Movie>> {
        val position = params.key ?: 1
        return movieSearchApi.search(
            clientId = "",
            secretKey = "",
            query = query,
            display = params.loadSize,
            start = params.loadSize * (position - 1) + 1,
        )
            .delay(1000L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .map { response ->
                mapper.transform(response)
            }
            .map { messages ->
                toLoadResult(messages, position)
            }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { LoadResult.Error(it) })
    }

    private fun toLoadResult(data: Movies, position: Int): LoadResult<Int, Movies.Movie> {
        return LoadResult.Page(
            data = data.movies,
            prevKey = if(position == 1) null else position - 1,
            nextKey = if(position == data.total) null else position + 1,
        )
    }
}