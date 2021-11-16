package com.citizenme.socialmediaapp.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @ColumnInfo(name = "album_id")
    val albumId : Int,

    @PrimaryKey
    @ColumnInfo(name = "photo_id")
    val photoId : Int,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "url")
    val url : String,

    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl : String
)