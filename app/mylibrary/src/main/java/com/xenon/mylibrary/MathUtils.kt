package com.xenon.mylibrary

import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double)

fun Point.calculateDistanceTo(point: Point): Double {
    return sqrt((x - point.x).pow(2) + (y -point.y).pow(2))
}