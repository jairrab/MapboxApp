package com.jairrab.data

import java.util.*
import kotlin.random.Random

object Rndom {

    fun string(): String {
        return UUID.randomUUID().toString()
    }

    fun int(): Int {
        return Random.nextInt(100000)
    }

    fun long(): Long {
        return Random.nextLong(10000000)
    }

    fun double(): Double {
        return Random.nextDouble(1000.0)
    }

    fun boolean(): Boolean {
        return Math.random() < 0.5
    }
}