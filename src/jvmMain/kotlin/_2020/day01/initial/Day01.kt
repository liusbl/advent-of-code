package _2020.day01.initial

import java.io.File

fun main() {
    // Started:
    // Finished:
    // Solution:
    solvePart1()

    // Started:dfsadas
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/_2020.day01/input/input.txt")
//    val input = File("src/jvmMain/kotlin/_2020.day01/input/input_part1_test.txt")
    val lines = input.readLines()

    val numbers = lines.map { it.toInt() }

    numbers.forEach { n1 ->
        numbers.forEach { n2 ->
            numbers.forEach { n3 ->
                if (n1 != n2 && n2 != n3 && n1 + n2 + n3 == 2020) {
                    println(n1 * n2 * n3)
                    return
                }
            }
        }
    }

    val result = "result"

    println(result)
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/_2020.day01/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}