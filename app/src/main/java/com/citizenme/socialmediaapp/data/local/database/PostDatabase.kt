package com.citizenme.socialmediaapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.citizenme.socialmediaapp.data.local.dao.CommentDao
import com.citizenme.socialmediaapp.data.local.dao.PhotoDao
import com.citizenme.socialmediaapp.data.local.dao.PostDao
import com.citizenme.socialmediaapp.data.local.entity.CommentEntity
import com.citizenme.socialmediaapp.data.local.entity.PhotoEntity
import com.citizenme.socialmediaapp.data.local.entity.PostEntity

@Database(
    entities = [PostEntity::class, PhotoEntity::class, CommentEntity::class],
    exportSchema = false,
    version = 1,
)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun photoDao(): PhotoDao
    abstract fun commentDao(): CommentDao

}