package com.jairrab.mapboxapp.ui.utils

import java.util.*
import javax.inject.Inject

class TimeUtils @Inject constructor() {

    val currentTime: Long
        get() = Calendar.getInstance().timeInMillis

    fun getElapsedString(time: Long): String {
        val dayElapsed = getTimeElapsedInDay(time)
        val day = dayElapsed.toInt()
        val fractionalDay = dayElapsed - day
        val hours = (fractionalDay * 24).toInt()
        val fractionalHours = fractionalDay * 24 - hours
        val minute = (fractionalHours * 60).toInt()
        val fractionalMinute = fractionalHours * 60 - minute
        val seconds = (fractionalMinute * 60).toInt()

        return getProperElapsedString(day, hours, minute, seconds)
    }

    fun getProperElapsedString(
        day: Int,
        hours: Int,
        minute: Int,
        seconds: Int
    ): String {
        return when {
            day > 2 -> String.format("%s days ago", day)
            day > 1 -> when {
                hours > 1 -> String.format("%s days & %s hours ago", day, hours)
                hours > 0 -> String.format("%s days & %s hour ago", day, hours)
                else      -> String.format("%s days ago", day)
            }
            day > 0 -> when {
                hours > 1 -> String.format("%s day & %s hours ago", day, hours)
                hours > 0 -> String.format("%s day & %s hour ago", day, hours)
                else      -> String.format("%s day ago", day)
            }
            else    -> when {
                hours > 1 -> when {
                    minute > 1 -> String.format("%s hours & %s minutes ago", hours, minute)
                    minute > 0 -> String.format("%s hours & %s minute ago", hours, minute)
                    else       -> String.format("%s hours ago", hours)
                }
                hours > 0 -> when {
                    minute > 1 -> String.format("%s hour & %s minutes ago", hours, minute)
                    minute > 0 -> String.format("%s hour & %s minute ago", hours, minute)
                    else       -> String.format("%s hour ago", hours)
                }
                else      -> when {
                    minute > 1 -> when {
                        seconds > 1 -> String.format("%s minutes & %s seconds ago", minute, seconds)
                        seconds > 0 -> String.format("%s minutes & %s second ago", minute, seconds)
                        else        -> String.format("%s minutes ago", minute)
                    }
                    minute > 0 -> when {
                        seconds > 1 -> String.format("%s minute & %s seconds ago", minute, seconds)
                        seconds > 0 -> String.format("%s minute & %s second ago", minute, seconds)
                        else        -> String.format("%s minute ago", minute)
                    }
                    else       -> when {
                        seconds > 1 -> String.format("%s seconds ago", seconds)
                        else        -> String.format("%s second ago", seconds)
                    }
                }
            }
        }
    }

    private fun getTimeElapsedInDay(time: Long): Double {
        val elapsed = currentTime - time
        return elapsed.toDouble() / 1000.0 / (24.0 * 60.0 * 60.0)
    }
}