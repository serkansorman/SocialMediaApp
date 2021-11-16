package com.citizenme.socialmediaapp.data.remote.repository

import com.citizenme.socialmediaapp.data.remote.service.PostService
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService
) {

    suspend fun getPosts() = postService.getPosts()

    suspend fun getComments(postId : Int) = postService.getComments(postId)
}