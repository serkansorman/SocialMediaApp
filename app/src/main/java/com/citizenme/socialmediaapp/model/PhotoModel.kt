package com.citizenme.socialmediaapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoModel(
    @SerializedName("albumId")
    val albumId : Int,
    @SerializedName("id")
    val photoId : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("url")
    val url : String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl : String
) : Parcelable