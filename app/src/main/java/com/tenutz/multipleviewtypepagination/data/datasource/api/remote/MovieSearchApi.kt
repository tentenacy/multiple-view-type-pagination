package com.tenutz.multipleviewtypepagination.data.datasource.api.remote

import com.tenutz.multipleviewtypepagination.data.datasource.api.dto.MoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieSearchApi {

    @GET("/v1/search/movie.json")
    fun search(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") query: String,
        @Query("display") display: Int = 20,
        @Query("start") start: Int = 1,
    ): Single<MoviesResponse>

}