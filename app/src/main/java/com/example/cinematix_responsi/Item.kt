package com.example.cinematix_responsi

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Item(
    var title : String?= null,
    var author : String?= null,
    var description : String?= null,
    var imageUrl : String?= null)