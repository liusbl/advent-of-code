package day04.initial

import java.io.File

fun main() {
//    solvePart1() // Solution: 21138, time: 07:09
    solvePart2() // Solution: 21138, time: 07:09
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day04/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day04/input/input_part1_test.txt")
    val lines = input.readLines()

    val res = lines.map { line ->
        val (winningLine, yourLine) = line.split(":")[1].split("|").map { it.trim() }
        val winning = winningLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val your = yourLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        your.filter { winning.contains(it) }
    }.filter { it.isNotEmpty() }
        .map { yourWinningNumbers ->
            yourWinningNumbers.fold(1) { acc, next ->
                acc * 2
            } / 2
        }.sum()

    println(res)

    val result = "result"
    println(result)
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day04/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day04/input/input_part1_test.txt")
    val lines = input.readLines()

    val res = lines.map { line ->
        val (winningLine, yourLine) = line.split(":")[1].split("|").map { it.trim() }
        val winning = winningLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val your = yourLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        your.filter { winning.contains(it) }
    }.filter { it.isNotEmpty() }.sumOf { yourWinningNumbers ->
        yourWinningNumbers.fold(1) { acc, next ->
            acc * 2
        } / 2
    }

    println(res)

    val result = "result"
    println(result)
}