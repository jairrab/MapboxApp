package com.jairrab.mapboxapp.ui.utils

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TimeUtilsTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getProperElapsedString() {
        val timeUtils = TimeUtils()

        val result1 = timeUtils.getProperElapsedString(1,2,3,45)
        val result2 = timeUtils.getProperElapsedString(10,23,3,5)
        val result3 = timeUtils.getProperElapsedString(0,22,58,45)
        val result4 = timeUtils.getProperElapsedString(0,0,45,23)

        Assert.assertEquals(result1, "1 day & 2 hours ago")
        Assert.assertEquals(result2, "10 days ago")
        Assert.assertEquals(result3, "22 hours & 58 minutes ago")
        Assert.assertEquals(result4, "45 minutes & 23 seconds ago")
    }
}