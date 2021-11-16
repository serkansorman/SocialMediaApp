package com.citizenme.socialmediaapp.utils

import com.citizenme.socialmediaapp.local.entity.CommentEntity
import com.citizenme.socialmediaapp.local.entity.PhotoEntity
import com.citizenme.socialmediaapp.local.entity.PostEntity
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostModel

fun PostModel.toPostEntity(): PostEntity = PostEntity(
    userId, postId, title, body
)

fun PhotoModel.toPhotoEntity(): PhotoEntity = PhotoEntity(
    albumId, photoId, title, url, thumbnailUrl
)

fun CommentModel.toCommentEntity(): CommentEntity = CommentEntity(
    postId, commentId, name, email, body
)

fun PostEntity.toPostModel(): PostModel = PostModel(
    userId, postId, title, body
)

fun PhotoEntity.toPhotoModel(): PhotoModel = PhotoModel(
    albumId, photoId, title, url, thumbnailUrl
)

fun CommentEntity.toCommentModel(): CommentModel = CommentModel(
    postId, commentId, name, email, body
)