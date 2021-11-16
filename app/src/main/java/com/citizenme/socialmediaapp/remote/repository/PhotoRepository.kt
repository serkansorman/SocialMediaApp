package com.citizenme.socialmediaapp.remote.repository

import com.citizenme.socialmediaapp.remote.service.PhotoService
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val photoService: PhotoService) {

    suspend fun getPhotos() = photoService.getPhotos()
}