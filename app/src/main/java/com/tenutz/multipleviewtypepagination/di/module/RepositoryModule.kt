package com.tenutz.multipleviewtypepagination.di.module

import com.tenutz.multipleviewtypepagination.data.datasource.paging.repository.MoviePagingRepository
import com.tenutz.multipleviewtypepagination.data.datasource.paging.repository.MoviePagingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMoviePagingRepository(repository: MoviePagingRepositoryImpl): MoviePagingRepository
}