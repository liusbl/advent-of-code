package day16.initial

import day11.initial.Grid
import day11.initial.Location
import day11.initial.toPrintableString
import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day16/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = Grid(lines).toPrintableString(includeLocation = false)

    println(result)
}

sealed class Image {
    abstract val char: Char

    sealed class Splitter(override val char: Char) : Image() {
        object Vertical : Splitter('|')

        object Horizontal : Splitter('-')
    }

    sealed class Mirror(override val char: Char) : Image() {
        object Forward : Mirror('/')

        object Backward : Mirror('\\')
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
        Image.Splitter.Vertical.char -> Image.Splitter.Vertical
        Image.Splitter.Horizontal.char -> Image.Splitter.Horizontal
        Image.Mirror.Forward.char -> Image.Mirror.Forward
        Image.Mirror.Backward.char -> Image.Mirror.Backward
        Image.Space.char -> Image.Space
        else -> error("Invalid image char: $char")
    }
