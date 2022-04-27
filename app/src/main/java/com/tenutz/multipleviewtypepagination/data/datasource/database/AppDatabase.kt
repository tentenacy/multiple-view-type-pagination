package com.tenutz.multipleviewtypepagination.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tenutz.multipleviewtypepagination.data.datasource.paging.dao.MovieDao
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import com.tenutz.multipleviewtypepagination.utils.converter.RoomConverters

@Database(
    entities = [
        Movies.Movie::class,
        Movies.MovieRemoteKeys::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        fun getInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
    }
}