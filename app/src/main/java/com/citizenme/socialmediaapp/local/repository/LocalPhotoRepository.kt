package com.citizenme.socialmediaapp.local.repository

import com.citizenme.socialmediaapp.local.dao.PhotoDao
import com.citizenme.socialmediaapp.local.entity.PhotoEntity
import javax.inject.Inject

class LocalPhotoRepository @Inject constructor(private val photoDao: PhotoDao) {

    suspend fun insertPhotos(vararg  photoEntities: PhotoEntity) =
        photoDao.insertAllPhotos(*photoEntities)

    suspend fun getPhotos(): MutableList<PhotoEntity> = photoDao.getAllPhotos()

    suspend fun getPhoto(postId : Int) : PhotoEntity = photoDao.getPhoto(postId)
}