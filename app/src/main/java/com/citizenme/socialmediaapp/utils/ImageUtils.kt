package com.citizenme.socialmediaapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("android:loadImage")
fun ImageView.loadImage(url: String?) = Glide.with(this.context)
    .load(
        GlideUrl(
            url,
            LazyHeaders.Builder()
                .addHeader("User-Agent", "User-Agent")
                .build()
        )
    )
    .thumbnail(0.5f)
    .apply(
        RequestOptions
            .centerCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(this.width, this.height)
    )
    .into(this)