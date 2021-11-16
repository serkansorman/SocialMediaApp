package com.citizenme.socialmediaapp.model

import com.google.gson.annotations.SerializedName

data class CommentModel(
    @SerializedName("postId")
    val postId : Int,
    @SerializedName("commentId")
    val commentId : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("body")
    val body : String
)