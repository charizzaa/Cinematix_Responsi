package com.example.cinematix_responsi

import android.support.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movie_table")
data class ItemDatabase(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int=0,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "author")
    var author: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String
)
{
    // Add a no-argument constructor for Firebase deserialization
    constructor() : this(0, "", "","","")}