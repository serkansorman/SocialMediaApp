package com.citizenme.socialmediaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostAndPhotoModel(
    val postModel: PostModel?,
    val photoModel: PhotoModel?
) : Parcelable