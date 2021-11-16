package com.citizenme.socialmediaapp.data.remote.service

import com.citizenme.socialmediaapp.model.PhotoModel
import retrofit2.Response
import retrofit2.http.GET

interface PhotoService {

    @GET("/photos")
    suspend fun getPhotos(): Response<MutableList<PhotoModel>>
}