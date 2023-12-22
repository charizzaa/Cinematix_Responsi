package com.example.cinematix_responsi

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movie_table")
data class Item(
    @PrimaryKey var id: String = "",
    var title : String?= null,
    var author : String?= null,
    var description : String?= null,
    var imageUrl : String?= null): Serializable