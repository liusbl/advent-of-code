package day11.initial

import day11.initial.Image.Galaxy
import day11.initial.Image.Space
import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day11/input/input_part1_test.txt")
    val grid = Grid(input.readLines())

    println(grid.toPrintableString(includeLocation = true))

    println()
}

//fun Grid<Image>.expandSpace(): Grid<Image> {
//    rowList.fold(listOf(emptyList<Location<Image>>())) { acc, next ->
//
//        acc
//    }
//}

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