package com.citizenme.socialmediaapp.data.remote.repository

import com.citizenme.socialmediaapp.data.remote.service.PhotoService
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val photoService: PhotoService) {

    suspend fun getPhotos() = photoService.getPhotos()
}