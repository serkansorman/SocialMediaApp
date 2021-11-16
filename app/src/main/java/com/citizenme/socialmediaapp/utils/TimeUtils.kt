package com.citizenme.socialmediaapp.utils


private const val oneMinuteRefreshTime = 60 * 1000 * 1000 * 1000L

fun needsRefreshFromApi(lastUpdateTime : Long?) =
    lastUpdateTime != null &&
            System.nanoTime() - lastUpdateTime > oneMinuteRefreshTime