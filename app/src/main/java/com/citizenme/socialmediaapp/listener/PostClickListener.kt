package com.citizenme.socialmediaapp.listener

import com.citizenme.socialmediaapp.model.Post

interface PostClickListener {

    fun onPostClick(post : Post)
}