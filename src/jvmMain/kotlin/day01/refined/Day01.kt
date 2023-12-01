package day01.refined

import day01.util.indexOfOrNull
import java.io.File

fun main() {
//    solvePart1() // Solution: 55816
    solvePart2() // Solution: 54980
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

fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/day01/input/input_part2_test.txt")
    val input = File("src/jvmMain/kotlin/day01/input/input.txt")
    val lines = input.readLines()

    val result = lines.sumOf { line ->
        val firstNumber = line.firstOfAny(allNumbers).convertToDigit()
        val lastNumber = line.reversed().firstOfAny(allNumbersReversed).reversed().convertToDigit()
        10 * firstNumber + lastNumber
    }

    println(result)
}

val wordNumbers = mapOf(
    "one" to 1, "two" to 2, "three" to 3, "four" to 4,
    "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
)
val allNumbers = (wordNumbers.keys + wordNumbers.values.map { it.toString() }).toList()
val allNumbersReversed = allNumbers.map { it.reversed() } // eno, owt, eerht, etc...

fun String.firstOfAny(possibilities: List<String>): String =
    possibilities.mapNotNull { number ->
        val index = indexOfOrNull(number)
        if (index == null) {
            return@mapNotNull null
        } else {
            number to index
        }
    }.minBy { it.second }.first

fun String.convertToDigit(): Int = if (this[0].isDigit()) toInt() else wordNumbers[this]!!.toInt()