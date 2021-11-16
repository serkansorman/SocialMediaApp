package com.citizenme.socialmediaapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @ColumnInfo(name = "post_id")
    val postId : Int,

    @PrimaryKey
    @ColumnInfo(name = "comment_id")
    val commentId : Int,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "email")
    val email : String,

    @ColumnInfo(name = "body")
    val body : String
)