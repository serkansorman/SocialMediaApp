package com.citizenme.socialmediaapp.mapper

import com.citizenme.socialmediaapp.data.local.entity.PhotoEntity
import com.citizenme.socialmediaapp.data.local.entity.PostEntity
import com.citizenme.socialmediaapp.model.PhotoModel
import com.citizenme.socialmediaapp.model.PostModel

fun PostModel.toPostEntity(): PostEntity = PostEntity(
    userId, postId, title, body
)

fun PhotoModel.toPhotoEntity(): PhotoEntity = PhotoEntity(
    albumId, photoId, title, url, thumbnailUrl
)