package day11.initial

import kotlin.math.abs

data class Location<T>(
    val value: T,
    val row: Int,
    val column: Int
)

fun <T> Location<T>.taxicabDistance(other: Location<T>): Int {
    val first = this
    val second = other
    return abs(first.row - second.row) + abs(first.column - second.column)
}
