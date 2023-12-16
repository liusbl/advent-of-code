package day16.initial

import day11.initial.Grid
import day11.initial.Location
import day11.initial.toPrintableString
import day11.initial.update
import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day16/input/input_part1_test.txt")
    val lines = input.readLines()

    val grid = Grid(lines)
    println("Input:")
    println(grid.toPrintableString(includeLocation = false))
    println()

    val collector = Collector(grid = grid)
        .run {
            copy(grid = this.grid.update(0, 0) { image ->
                image.update(energized = true, beam = Beam(Direction.Right))
            })
        }

    println("Initial beam:")
    println(collector)
    println()
}

sealed class Image {
    abstract val char: Char
    abstract val energized: Boolean
    abstract val beam: Beam?

    abstract fun update(energized: Boolean, beam: Beam?): Image

    sealed class Splitter(
        override val char: Char,
        override val energized: Boolean,
        override val beam: Beam?
    ) : Image() {
        data class Vertical(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Splitter('|', energized, beam) {
            override fun update(energized: Boolean, beam: Beam?): Image = copy(energized = energized, beam = beam)

            override fun toString(): String = toPrintableString()
        }

        data class Horizontal(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Splitter('-', energized, beam) {
            override fun update(energized: Boolean, beam: Beam?): Image = copy(energized = energized, beam = beam)

            override fun toString(): String = toPrintableString()
        }
    }

    sealed class Mirror(
        override val char: Char,
        override val energized: Boolean,
        override val beam: Beam?
    ) : Image() {
        data class Forward(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Mirror('/', energized, beam) {
            override fun update(energized: Boolean, beam: Beam?): Image = copy(energized = energized, beam = beam)

            override fun toString(): String = toPrintableString()
        }

        data class Backward(
            override val energized: Boolean,
            override val beam: Beam?
        ) : Mirror('\\', energized, beam) {
            override fun update(energized: Boolean, beam: Beam?): Image = copy(energized = energized, beam = beam)

            override fun toString(): String = toPrintableString()
        }
    }

    data class Space(
        override val energized: Boolean,
        override val beam: Beam?
    ) : Image() {
        override val char = '.'

        override fun update(energized: Boolean, beam: Beam?): Image = copy(energized = energized, beam = beam)

        override fun toString(): String = toPrintableString()
    }

    fun toPrintableString(): String = when {
        beam != null -> beam!!.direction.char.toString()
        energized -> "E"
        else -> char.toString()
    }
}

data class Collector(
    val grid: Grid<Image>
) {
    override fun toString(): String = grid.toPrintableString(includeLocation = false)
}

data class Beam(
    val direction: Direction
)

enum class Direction(val char: Char) {
    Up('↑'),
    Right('→'),
    Down('↓'),
    Left('←')
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
