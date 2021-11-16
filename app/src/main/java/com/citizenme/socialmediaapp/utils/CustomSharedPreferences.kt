package com.citizenme.socialmediaapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager


class CustomSharedPreferences {

    companion object {

        const val POST_LIST_UPDATE_TIME = "post_list_update_time"
        const val COMMENT_LIST_UPDATE_TIME = "comment_list_update_time"

        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context): CustomSharedPreferences = instance ?: synchronized(
            lock
        ) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun saveLastUpdateTime(time: Long, updatedListName: String) {
        sharedPreferences?.edit(commit = true) {
            putLong(updatedListName, time)
        }
    }

    fun getLastUpdateTime(updatedListName: String) = sharedPreferences?.getLong(updatedListName, 0)

}