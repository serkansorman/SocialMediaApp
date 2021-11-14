package com.citizenme.socialmediaapp.api.repository

import com.citizenme.socialmediaapp.api.service.PhotoService
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val photoService: PhotoService) {

    suspend fun getPhotos() = photoService.getPhotos()
}