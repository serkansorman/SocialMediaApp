package com.citizenme.socialmediaapp.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @PrimaryKey
    @ColumnInfo(name = "post_id")
    val postId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    val body: String
)