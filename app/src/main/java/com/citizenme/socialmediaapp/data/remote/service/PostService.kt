package com.citizenme.socialmediaapp.data.remote.service

import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("/posts")
    suspend fun getPosts(): Response<MutableList<PostModel>>

    @GET("/posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): Response<List<CommentModel>>
}