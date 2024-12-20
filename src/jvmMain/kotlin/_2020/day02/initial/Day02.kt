package _2020.day02.initial

import java.io.File

fun main() {
    // Started:
    // Finished:
    // Solution:
//    solvePart1()

    // Started:dfsadas
    // Finished:
    // Solution:
    solvePart2()
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/_2020/day02/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/_2020/day02/input/input.txt")
    val lines = input.readLines()

    val result = lines.count { line ->
        val (countRangeString, letter, password) = line.split(" ").filter { it.isNotBlank() }

        val (start, end) = countRangeString.split("-").map(String::toInt)

        password.count { it == letter[0] } in (start..end)
    }

    println(result)
}

fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/_2020/day02/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/_2020/day02/input/input.txt")
    val lines = input.readLines()

    val result = lines.count { line ->
        val (positionListString, letter, password) = line.split(" ").filter { it.isNotBlank() }

        val (start, end) = positionListString.split("-").map(String::toInt)

        (password[start - 1] == letter[0]) xor (password[end - 1] == letter[0])
    }

    println(result)
}