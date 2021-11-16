package com.citizenme.socialmediaapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel (
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("id")
    val postId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String
) : Parcelable