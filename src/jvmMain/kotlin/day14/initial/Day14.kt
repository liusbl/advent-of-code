package day14.initial

import day11.initial.Grid
import day11.initial.Location
import day11.initial.toPrintableString
import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day14/input/input_part1_test.txt")
    val grid = Grid(input.readLines())

    val result = grid.toPrintableString(includeLocation = false)
    println(result)
    println()

    val a = grid.columnList.map { row ->
        val result = row.joinToString("") { it.toPrintableString() }
            .split("#")
            .joinToString("#") { section ->
                val (rocks, space) = section.partition { it == 'O' }
                rocks + space
            }
        result
    }

    println(Grid(a).toPrintableString(includeLocation = false))
    println()

    println(Grid(a).columnList .flatten().let(::Grid).toPrintableString(includeLocation = false))
    println()


}

sealed class Image {
    abstract val char: Char

    object Rock : Image() {
        override val char = 'O'
    }

    object StableRock : Image() {
        override val char = '#'
    }

    object Space : Image() {
        override val char = '.'
    }

    override fun toString(): String = char.toString()
}

fun Grid(lines: List<String>): Grid<Image> {
    val list = lines.flatMapIndexed { row, line ->
        line.mapIndexed { column, char ->
            Location(value = Image(char), row = row, column = column)
        }
    }
    return Grid(list)
}

fun Image(char: Char): Image =
    when (char) {
        'O' -> Image.Rock
        '#' -> Image.StableRock
        '.' -> Image.Space
        else -> error("Invalid image char: $char")
    }
