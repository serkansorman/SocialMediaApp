package com.citizenme.socialmediaapp.local.repository

import com.citizenme.socialmediaapp.local.dao.CommentDao
import com.citizenme.socialmediaapp.local.entity.CommentEntity
import javax.inject.Inject

class LocalCommentRepository @Inject constructor(private val commentDao: CommentDao) {

    suspend fun insertComments(vararg commentEntities: CommentEntity) =
        commentDao.insertAllComments(*commentEntities)

    suspend fun getComments(): MutableList<CommentEntity> = commentDao.getAllComments()

}