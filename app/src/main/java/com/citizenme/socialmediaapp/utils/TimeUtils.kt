package com.citizenme.socialmediaapp.utils


private const val tenMinutesRefreshTime = 10 * 60 * 1000 * 1000 * 1000L
val customPreferences = CustomSharedPreferences()

fun needsRefreshFromApi(lastUpdateTime: Long? = customPreferences.getLastUpdateTime()) =
    lastUpdateTime != null &&
    lastUpdateTime != 0L &&
    System.nanoTime() - lastUpdateTime > tenMinutesRefreshTime