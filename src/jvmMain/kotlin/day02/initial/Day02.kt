package day02.initial

import util.add
import util.removeAt
import java.io.File
import kotlin.math.abs

fun main() {
    // Started: 2024-12-02 20:20
    // Finished: 2024-12-02 20:30
    // Solution: 202
    //solvePart1()

    // Started: 2024-12-02 20:30
    // Finished: 2024-12-02 20:39
    // Solution: 271
    solvePart2()
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day02/input/input.txt")
    val lines = input.readLines()

    val count = lines.filter { line ->
        val initialNumbers = line.split(" ").map { it.toInt() }
        val numberLists = List(initialNumbers.size) { index ->
            initialNumbers.removeAt(index)
        }.add(initialNumbers)

        numberLists.any { numbers ->
            val sortedCondition = numbers.sorted() == numbers || numbers.sortedDescending() == numbers
            if (!sortedCondition) {
                return@any false
            }

            val differences = numbers.zipWithNext().map { abs(it.first - it.second) }
            val limitCondition = differences.max() <= 3 && differences.min() >= 1

            if (!limitCondition) {
                return@any false
            }

            true
        }
    }.count()

    println(count)
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day02/input/input.txt")
    val lines = input.readLines()

    val count = lines.filter { line ->
        val numbers = line.split(" ").map { it.toInt() }

        val sortedCondition = numbers.sorted() == numbers || numbers.sortedDescending() == numbers
        if (!sortedCondition) {
            return@filter false
        }

        val differences = numbers.zipWithNext().map { abs(it.first - it.second) }
        val limitCondition = differences.max() <= 3 && differences.min() >= 1

        if (!limitCondition) {
            return@filter false
        }

        true
    }.count()

    println(count)
}

// Rows: reports. numbers: levels