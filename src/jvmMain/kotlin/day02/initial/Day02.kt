package day02.initial

import java.io.File

fun main() {
    // Started: 2025-12-02 14:45
    // Finished: 2025-12-02 14:55
    // Solution: 19128774598
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day02/input/input.txt")
    val lines = input.readLines()[0].split(",")

    var sum = 0L

    lines.forEach { line ->
        val (start, end) = line.split("-").map { it.toLong() }

        val invalidNumbers = (start..end).mapNotNull { id ->
            val idString = id.toString()
            if (idString.length % 2 != 0) {
                null // Example: 101, 3
            } else {
                if (idString.take(idString.length / 2) == idString.takeLast(idString.length / 2)) {
                    id
                } else {
                    null
                }
            }
        }

        sum += invalidNumbers.sum()
        println("Start: $start, end: $end, invalid count: ${invalidNumbers.count()}")
    }

    val result = sum

    println(result)
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day02/input/input_part2_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}