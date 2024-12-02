package _2023.day11.initial

import kotlin.math.abs

// TODO consider if Location should have an inner object like Position to contains row and column.
//  Thus you could pass them as one single object.
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
