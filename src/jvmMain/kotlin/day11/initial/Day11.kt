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

    println(grid.toPrintableString())
}

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
            Location(Image(char), column, row)
        }
    }
    return Grid(list)
}

fun Grid<Image>.toPrintableString(): String = rowList.joinToString(separator = "\n") { row ->
    row.joinToString(separator = "") { location -> location.toPrintableString() }
}

fun Image(char: Char): Image =
    when (char) {
        '#' -> Galaxy(char)
        '.' -> Space
        else -> error("Invalid image char: $char")
    }

fun Location<Image>.toPrintableString(): String = value.toString()