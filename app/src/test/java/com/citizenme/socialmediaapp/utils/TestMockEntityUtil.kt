package com.citizenme.socialmediaapp.utils

import com.citizenme.socialmediaapp.data.local.entity.CommentEntity
import com.citizenme.socialmediaapp.data.local.entity.PhotoEntity
import com.citizenme.socialmediaapp.data.local.entity.PostEntity
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.model.PostModel

fun getMockPostEntity() = PostEntity(
    userId = 1,
    postId =  1,
    title = "Title",
    body = "body"
)

fun getMockPhotoEntity() = PhotoEntity(
    albumId = 1,
    photoId = 1,
    title = "Title",
    url = "url",
    thumbnailUrl = "thumbnailUrl"
)

fun getMockCommentEntity() = CommentEntity(
    postId = 1,
    commentId = 1,
    name = "name",
    email = "email",
    body = "body"
)
