package com.citizenme.socialmediaapp.mapper

import com.citizenme.socialmediaapp.data.local.entity.CommentEntity
import com.citizenme.socialmediaapp.data.local.entity.PhotoEntity
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PhotoModel

fun PhotoEntity.toPhotoModel(): PhotoModel = PhotoModel(
    albumId, photoId, title, url, thumbnailUrl
)

fun CommentEntity.toCommentModel(): CommentModel = CommentModel(
    postId, commentId, name, email, body
)