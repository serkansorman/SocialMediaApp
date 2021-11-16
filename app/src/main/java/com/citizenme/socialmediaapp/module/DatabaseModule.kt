package com.citizenme.socialmediaapp.module

import android.content.Context
import androidx.room.Room
import com.citizenme.socialmediaapp.MainApplication
import com.citizenme.socialmediaapp.local.dao.CommentDao
import com.citizenme.socialmediaapp.local.dao.PhotoDao
import com.citizenme.socialmediaapp.local.dao.PostDao
import com.citizenme.socialmediaapp.local.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideContext(): Context = MainApplication()

    @Provides
    @Singleton
    fun provideNoteDataBase(@ApplicationContext context: Context): PostDatabase =
        Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "postDatabase"
        ).build()


    @Provides
    @Singleton
    fun providePostDao(database: PostDatabase): PostDao {
        return database.postDao()
    }

    @Provides
    @Singleton
    fun providePhotoDao(database: PostDatabase): PhotoDao {
        return database.photoDao()
    }

    @Provides
    @Singleton
    fun provideCommentDao(database: PostDatabase): CommentDao {
        return database.commentDao()
    }
}