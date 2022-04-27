package com.tenutz.multipleviewtypepagination.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Movies.Movie>)

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Movies.Movie>

    @Query("DELETE FROM movies")
    fun clearMovies()
}