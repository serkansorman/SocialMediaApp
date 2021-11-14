package com.citizenme.socialmediaapp.listener

import com.citizenme.socialmediaapp.model.PostAndPhotoModel

interface PostClickListener {

    fun onPostClick(postAndPhotoModel: PostAndPhotoModel)
}