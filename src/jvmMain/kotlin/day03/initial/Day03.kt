package day03.initial

import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
    val lines = input.readLines()

    var numberStarted = false
    var symbolAppeared = false
    var interimNumber = ""
    val result = mutableListOf<Int>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { column, char ->
            if (numberStarted && char == '.') {
                if (symbolAppeared) {
                    result.add(interimNumber.toInt())
                }
                numberStarted = false
                symbolAppeared = false
                interimNumber = ""
                return@forEachIndexed
            } else if (numberStarted && !char.isDigit()) {
                numberStarted = false
                symbolAppeared = false
                result.add(interimNumber.toInt())
                interimNumber = ""
                return@forEachIndexed
            }
            if (char.isDigit()) {
                // Start number tracking and symbol tracking
                numberStarted = true
                interimNumber += char
            }
            // If symbol exists close, change symbol flag
            if (numberStarted && lines.adjacent(row, column).any { !it.isDigit() && it != '.' }) {
                symbolAppeared = true
            }
        }
        if (numberStarted && symbolAppeared) {
            result.add(interimNumber.toInt())
        }
        numberStarted = false
        symbolAppeared = false
        interimNumber = ""
    }


    println(result.sum())
}

fun List<String>.adjacent(row: Int, column: Int): List<Char> {
    val lines = this
    return listOf(
        lines.safeGet(row - 1, column - 1),
        lines.safeGet(row - 1, column),
        lines.safeGet(row - 1, column + 1),
        lines.safeGet(row, column - 1),
        lines.safeGet(row, column + 1),
        lines.safeGet(row + 1, column - 1),
        lines.safeGet(row + 1, column),
        lines.safeGet(row + 1, column + 1)
    )
}

fun List<String>.safeGet(row: Int, column: Int): Char {
    val lines = this
    val maxRow = lines.size - 1

    val line = lines[row.coerceIn(0..maxRow)]
    val maxColumn = line.length - 1
    return line[column.coerceIn(0..maxColumn)]
}

data class Number(
    val row: Int,
    val column: Int,
    val value: String
)