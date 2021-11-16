package com.citizenme.socialmediaapp.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.citizenme.socialmediaapp.local.dao.CommentDao
import com.citizenme.socialmediaapp.local.dao.PhotoDao
import com.citizenme.socialmediaapp.local.dao.PostDao
import com.citizenme.socialmediaapp.local.entity.CommentEntity
import com.citizenme.socialmediaapp.local.entity.PhotoEntity
import com.citizenme.socialmediaapp.local.entity.PostEntity

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