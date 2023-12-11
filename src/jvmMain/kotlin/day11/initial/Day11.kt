package day11.initial

import day11.initial.Image.Galaxy
import day11.initial.Image.Space
import util.add
import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day11/input/input_part1_test.txt")
    val grid = Grid(input.readLines())

    println("Initial grid:")
    println(grid.toPrintableString(includeLocation = false))
    println(grid.toPrintableString(includeLocation = true))
    println()

    println("Expanded grid:")
    val expandedGrid = grid.expandSpace()
    println(expandedGrid.toPrintableString(includeLocation = false))
    println(expandedGrid.toPrintableString(includeLocation = true))
    println()

    val pairList = expandedGrid.getPairList()
    println("Pair list. Size: ${pairList.size}. Content:")
    println(pairList.joinToString("\n")/* { (first, second) -> "$first, $second, distance: ${first.taxicabDistance(second)}" }*/)
    println()

    val distanceSum = pairList.sumOf { it.distance }
    println("Distance sum: $distanceSum")
}

data class GalaxyPair(
    val first: Location<Image>,
    val second: Location<Image>,
    val distance: Int
)

fun GalaxyPair(pair: Pair<Location<Image>, Location<Image>>): GalaxyPair =
    GalaxyPair(
        first = pair.first,
        second = pair.second,
        distance = pair.first.taxicabDistance(pair.second)
    )

fun Grid<Image>.getPairList(): List<GalaxyPair> {
    val galaxyList = this.rowList.flatten().filter { it.value is Galaxy }
    return galaxyList.fold(emptyList<Pair<Location<Image>, Location<Image>>>()) { acc, firstGalaxy ->
        acc + galaxyList.flatMap { secondGalaxy ->
            if (firstGalaxy == secondGalaxy || acc.contains(firstGalaxy to secondGalaxy) || acc.contains(secondGalaxy to firstGalaxy)) {
                listOf(null)
            } else {
                listOf(firstGalaxy to secondGalaxy)
            }
        }.filterNotNull()
    }.map(::GalaxyPair)
}

fun Grid<Image>.expandSpace(): Grid<Image> {
    val expandedRowGrid = rowList.fold(listOf(emptyList<Location<Image>>()) to 0) { (acc, additional), row ->
        val newRow = row.map { it.copy(row = it.row + additional) }
        if (newRow.all { it.value == Space }) {
            val updatedNewRow = row.map { it.copy(row = it.row + additional + 1) }
            acc.add(newRow).add(updatedNewRow) to additional + 1
        } else {
            acc.add(newRow) to additional
        }
    }.first.flatten().let(::Grid)

    val expandedColumnGrid =
        expandedRowGrid.columnList.fold(listOf(emptyList<Location<Image>>()) to 0) { (acc, additional), column ->
            val newColumn = column.map { it.copy(column = it.column + additional) }
            if (newColumn.all { it.value == Space }) {
                val newUpdatedColumn = column.map { it.copy(column = it.column + additional + 1) }
                acc.add(newColumn).add(newUpdatedColumn) to additional + 1
            } else {
                acc.add(newColumn) to additional
            }
        }.first.flatten().let(::Grid)
    return expandedColumnGrid
}

// ----------- Parsing

sealed interface Image {
    data class Galaxy(val char: Char) : Image {
        override fun toString(): String = char.toString()
    }

    object Space : Image {
        override fun toString(): String = "."
    }
}

fun Grid(lines: List<String>): Grid<Image> {
    val list = lines.flatMapIndexed { row, line ->
        line.mapIndexed { column, char ->
            Location(value = Image(char), row = row, column = column)
        }
    }
    return Grid(list)
}

fun Grid<Image>.toPrintableString(includeLocation: Boolean): String =
    if (includeLocation) {
        rowList.joinToString(separator = "\n") { row ->
            row.joinToString(separator = " | ") { "${it.value},${it.row},${it.column}" }
        }
    } else {
        rowList.joinToString(separator = "\n") { row ->
            row.joinToString(separator = "") { location -> location.toPrintableString() }
        }
    }

fun Image(char: Char): Image =
    when (char) {
        '#' -> Galaxy(char)
        '.' -> Space
        else -> error("Invalid image char: $char")
    }

fun Location<Image>.toPrintableString(): String = value.toString()