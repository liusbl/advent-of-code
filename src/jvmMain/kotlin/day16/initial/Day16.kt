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
    abstract val energized: Boolean
    abstract val beam: Beam?

    sealed class Splitter(
        override val char: Char,
        override val energized: Boolean,
        override val beam: Beam?
    ) : Image() {
        data class Vertical(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Splitter('|', energized, beam)

        data class Horizontal(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Splitter('-', energized, beam)
    }

    sealed class Mirror(
        override val char: Char,
        override val energized: Boolean,
        override val beam: Beam?
    ) : Image() {
        data class Forward(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Mirror('/', energized, beam)

        data class Backward(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Mirror('\\', energized, beam)
    }

    data class Space(
        override val energized: Boolean,
        override val beam: Beam?
    ) : Image() {
        override val char = '.'
    }

    override fun toString(): String = char.toString()
}

data class Collector(
    val beamList: List<Beam>,
    val grid: Grid<Image>
)

data class Beam(
    val direction: Direction
)

enum class Direction {
    Up,
    Right,
    Down,
    Left
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
        '|' -> Image.Splitter.Vertical(energized = false, beam = null)
        '-' -> Image.Splitter.Horizontal(energized = false, beam = null)
        '/' -> Image.Mirror.Forward(energized = false, beam = null)
        '\\' -> Image.Mirror.Backward(energized = false, beam = null)
        '.' -> Image.Space(energized = false, beam = null)
        else -> error("Invalid image char: $char")
    }
