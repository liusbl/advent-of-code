package day14.initial

import day11.initial.Grid
import day11.initial.Location
import day11.initial.rotate
import day11.initial.toPrintableString
import java.io.File

fun main() {
    solvePart1() // Solution: 112046, Time: 2023-12-15 23:41
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day14/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day14/input/input.txt")
    val grid = Grid(input.readLines())

    println("Input:")
    println(grid.toPrintableString(includeLocation = false))
    println()

    val tiltedGrid = grid.columnList.map { row ->
        row.joinToString("") { it.toPrintableString() }
            .split("#")
            .joinToString("#") { section ->
                val (rocks, space) = section.partition { it == 'O' }
                rocks + space
            }
    }.let(::Grid).rotate()

    println("Tilted grid:")
    println(tiltedGrid.toPrintableString(includeLocation = false))
    println()

    val columnSize = tiltedGrid.columnList[0].size
    val result = tiltedGrid.rowList.mapIndexed { rowIndex, row ->
        row.sumOf { location ->
            when(location.value) {
                Image.Rock -> columnSize - rowIndex
                Image.Space -> 0
                Image.StableRock -> 0
            }
        }
    }.sum()

    println("Result: $result")
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
