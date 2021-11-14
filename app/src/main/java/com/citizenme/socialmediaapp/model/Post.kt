package com.citizenme.socialmediaapp.model

data class Post(
    val userId : Int,
    val postId : Int,
    val title : String,
    val body : String
)