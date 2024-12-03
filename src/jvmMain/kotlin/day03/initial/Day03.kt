package day03.initial

import java.io.File

fun main() {
    // Started: 2024-12-03 11:06
    // Finished: 2024-12-03 11:22
    // Solution: 170778545
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

// 31705549: That's not the right answer; your answer is too low.
//  Reason: I wasn't taking all the lines

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
    val line = input.readLines().joinToString("")

    val matcher = "mul\\((\\d+),(\\d+)\\)".toRegex()

    val matchList = matcher.findAll(line)
    val result = matchList.sumOf { match ->
        val first = match.groups[1]?.value?.toInt()!!
        val second = match.groups[2]?.value?.toInt()!!
        first * second
    }

    println(result)
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/dayNN/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}