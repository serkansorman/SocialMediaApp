package com.citizenme.socialmediaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.citizenme.socialmediaapp.data.local.entity.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(vararg postEntities: PostEntity)

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): MutableList<PostEntity>

    @Query("SELECT * FROM posts WHERE post_id=:id")
    suspend fun getPost(id: Int): PostEntity
}