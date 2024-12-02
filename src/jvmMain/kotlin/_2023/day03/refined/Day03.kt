package _2023.day03.refined

import java.io.File

fun main() {
    solvePart1()  // Solution: 532428, solved at 07:34
//    solvePart2() // Solution: 84051670, soved at 07:59
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day03/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
    val lines = input.readLines()

    var numberStarted = false
    var symbolAppeared = false
    var interimNumber = ""

    fun reset() {
        numberStarted = false
        symbolAppeared = false
        interimNumber = ""
    }

    val result = mutableListOf<Int>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { column, char ->
            if (numberStarted && char == '.') {
                if (symbolAppeared) {
                    result.add(interimNumber.toInt())
                }
                reset()
                return@forEachIndexed
            } else if (numberStarted && !char.isDigit()) {
                result.add(interimNumber.toInt())
                reset()
                return@forEachIndexed
            }
            if (char.isDigit()) {
                // Start number tracking and symbol tracking
                numberStarted = true
                interimNumber += char
            }
            // If symbol exists close, change symbol flag
            if (numberStarted && lines.adjacent(row, column).any { !it.char.isDigit() && it.char != '.' }) {
                symbolAppeared = true
            }
        }
        if (numberStarted && symbolAppeared) {
            result.add(interimNumber.toInt())
        }
        reset()
    }

    println(result.sum())
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
    val lines = input.readLines()

    var numberStarted = false
    var gearPositionSet = emptySet<Point>()
    var interimNumber = ""
    val result = mutableListOf<Gear>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { column, char ->
            if (numberStarted && char == '.') {
                if (gearPositionSet.isNotEmpty()) {
                    result.add(Gear(interimNumber.toInt(), gearPositionSet))
                }
                numberStarted = false
                gearPositionSet = emptySet()
                interimNumber = ""
                return@forEachIndexed
            } else if (numberStarted && !char.isDigit()) {
                result.add(Gear(interimNumber.toInt(), gearPositionSet))
                numberStarted = false
                gearPositionSet = emptySet()
                interimNumber = ""
                return@forEachIndexed
            }
            if (char.isDigit()) {
                // Start number tracking and symbol tracking
                numberStarted = true
                interimNumber += char
            }
            // If symbol exists close, change symbol flag
            if (numberStarted) {
                gearPositionSet = gearPositionSet.toMutableSet().apply {
                    addAll(lines.adjacent(row, column).filter { it.char == '*' })
                }
            }
        }
        if (numberStarted && gearPositionSet.isNotEmpty()) {
            result.add(Gear(interimNumber.toInt(), gearPositionSet))
        }
        numberStarted = false
        gearPositionSet = emptySet()
        interimNumber = ""
    }

    val gearPointList = result.flatMap { it.gearPointSet.toList() }

    val gearPointMap = gearPointList.associateWith { gear -> result.filter { it.gearPointSet.contains(gear) } }

    val sum = gearPointMap.filter { it.value.size == 2 }
        .toList()
        .sumOf { it.second[0].number * it.second[1].number }

    println(sum)
}

fun List<String>.adjacent(row: Int, column: Int): List<Point> {
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

fun List<String>.safeGet(row: Int, column: Int): Point {
    val lines = this
    val maxRow = lines.size - 1

    val rowCoerced = row.coerceIn(0..maxRow)
    val line = lines[rowCoerced]
    val maxColumn = line.length - 1
    val columnCoerced = column.coerceIn(0..maxColumn)
    return Point(line[columnCoerced], Position(rowCoerced, columnCoerced))
}

data class Point(
    val char: Char,
    val position: Position
)
data class Gear(
    val number: Int,
    val gearPointSet: Set<Point>
)

data class Position(
    val row: Int,
    val column: Int
)

data class Number(
    val row: Int,
    val column: Int,
    val value: String
)