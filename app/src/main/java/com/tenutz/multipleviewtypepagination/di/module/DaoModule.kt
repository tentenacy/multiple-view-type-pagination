package com.tenutz.multipleviewtypepagination.di.module

import android.content.Context
import com.tenutz.multipleviewtypepagination.data.datasource.paging.dao.MovieDao
import com.tenutz.multipleviewtypepagination.data.datasource.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =  AppDatabase.getInstance(context)
}