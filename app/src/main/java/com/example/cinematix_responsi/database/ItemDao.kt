package com.example.cinematix_responsi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinematix_responsi.ItemDatabase

@Dao
interface ItemDao {
    @Query("SELECT * FROM movie_table")
    fun getAllFilm(): LiveData<List<ItemDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: List<ItemDatabase>)

    @Delete
    fun deleteFilm(film: ItemDatabase)

    @Query("DELETE FROM movie_table")
    fun deleteAllFilm()
}