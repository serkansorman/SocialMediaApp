package com.citizenme.socialmediaapp.utils

import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.model.PostModel

fun getMockPostModel() = PostModel(
    userId = 1,
    postId =  1,
    title = "Title",
    body = "body"
)

fun getMockPhotoModel() = PhotoModel(
    albumId = 1,
    photoId = 1,
    title = "Title",
    url = "url",
    thumbnailUrl = "thumbnailUrl"
)

fun getMockCommentModel() = CommentModel(
    postId = 1,
    commentId = 1,
    name = "name",
    email = "email",
    body = "body"
)

fun getMockPostAndPhotoModel() = PostAndPhotoModel(
    postModel = getMockPostModel(),
    photoModel = getMockPhotoModel()
)