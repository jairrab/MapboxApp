package com.jairrab.presentation.utils

import kotlin.math.pow
import kotlin.math.roundToLong

fun Double.roundToNDecimal(decimalPlace: Int): Double {
    val d = 10.0.pow(decimalPlace.toDouble())
    return (this * d).roundToLong() / d
}