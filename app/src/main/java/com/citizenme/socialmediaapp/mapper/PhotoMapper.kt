package com.citizenme.socialmediaapp.mapper

import com.citizenme.socialmediaapp.data.local.entity.CommentEntity
import com.citizenme.socialmediaapp.data.local.entity.PostEntity
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PostModel

fun CommentModel.toCommentEntity(): CommentEntity = CommentEntity(
    postId, commentId, name, email, body
)

fun PostEntity.toPostModel(): PostModel = PostModel(
    userId, postId, title, body
)