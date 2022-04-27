package com.tenutz.multipleviewtypepagination.di.module

import com.tenutz.multipleviewtypepagination.BuildConfig
import com.tenutz.multipleviewtypepagination.data.datasource.api.remote.MovieSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
    }

    @Provides
    fun provideMovieSearchApi(retrofitBuilder: Retrofit.Builder): MovieSearchApi {
        return retrofitBuilder
            .baseUrl("https://openapi.naver.com")
            .build()
            .create()
    }
}