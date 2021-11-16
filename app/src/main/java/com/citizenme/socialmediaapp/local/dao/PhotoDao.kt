package com.citizenme.socialmediaapp.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.citizenme.socialmediaapp.local.entity.PhotoEntity

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(vararg photoEntities: PhotoEntity)

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotos(): MutableList<PhotoEntity>

    @Query("SELECT * FROM photos WHERE album_id=:postId")
    suspend fun getPhoto(postId: Int): PhotoEntity
}