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
    // Mendapatkan semua data film dari tabel movie_table secara live (real-time)
    @Query("SELECT * FROM movie_table")
    fun getAllFilm(): LiveData<List<ItemDatabase>>

    // Menyisipkan atau mengganti data film ke dalam tabel movie_table,
    // jika terjadi konflik, data yang ada akan diganti dengan data baru.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: List<ItemDatabase>)

    // Menghapus satu data film dari tabel movie_table
    @Delete
    fun deleteFilm(film: ItemDatabase)

    // Menghapus semua data film dari tabel movie_table
    @Query("DELETE FROM movie_table")
    fun deleteAllFilm()
}