package com.citizenme.socialmediaapp.utils

import org.junit.Assert.*

import org.junit.Test

class TimeUtilsTest {

    private val oneMinuteRefreshTime = 60 * 1000 * 1000 * 1000L

    @Test
    fun `test needs refresh from api`() {
        val lastUpdatedTime = System.nanoTime() - oneMinuteRefreshTime

        assertTrue(needsRefreshFromApi(lastUpdatedTime))
    }
}