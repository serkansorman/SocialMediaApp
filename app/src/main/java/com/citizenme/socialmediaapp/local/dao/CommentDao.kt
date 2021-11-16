package com.citizenme.socialmediaapp.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.citizenme.socialmediaapp.local.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(vararg commentEntities: CommentEntity)

    @Query("SELECT * FROM comments")
    suspend fun getAllComments(): MutableList<CommentEntity>
}