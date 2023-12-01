package day01.refined

import java.io.File

fun main() {
    solvePart1() // Solution: 55816
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day01/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day01/input/input.txt")
    val lines = input.readLines()
    val result = lines.sumOf { line ->
        "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt()
    }
    println(result)
}