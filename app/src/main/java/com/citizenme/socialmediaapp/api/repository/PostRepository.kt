package com.citizenme.socialmediaapp.api.repository

import com.citizenme.socialmediaapp.api.service.PostService
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService
) {

    suspend fun getPosts() = postService.getPosts()

    suspend fun getComments(postId : Int) = postService.getComments(postId)
}