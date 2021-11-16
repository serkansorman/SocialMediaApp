package com.citizenme.socialmediaapp.local.repository

import com.citizenme.socialmediaapp.local.dao.PostDao
import com.citizenme.socialmediaapp.local.entity.PostEntity
import javax.inject.Inject

class LocalPostRepository @Inject constructor(private val postDao: PostDao) {

    suspend fun insertPosts(vararg postEntities: PostEntity) =
        postDao.insertAllPosts(*postEntities)

    suspend fun getPosts(): MutableList<PostEntity> = postDao.getAllPosts()

    suspend fun getPost(id: Int): PostEntity = postDao.getPost(id)
}