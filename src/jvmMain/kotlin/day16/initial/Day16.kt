package day16.initial

import day11.initial.*
import util.remove
import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day16/input/input_part2_test.txt")
    val lines = input.readLines()

    val grid = Grid(lines)
    println("Input:")
    println(grid.toPrintableString(includeLocation = false))
    println()

    val initialBeamGrid = grid.update(0, 0) { image ->
        image.update(energized = true, beamList = listOf(Beam(Direction.Right)))
    }

    println("Initial beam:")
    println(initialBeamGrid.toPrintableString(includeLocation = false))
    println()

    var adv: Grid<Image> = initialBeamGrid
    repeat((1..22).count()) {
        adv = adv.advance()
        println("Advanced")
        println(adv.toPrintableString(includeLocation = false))
        println()
    }
}

fun Grid<Image>.advance(): Grid<Image> {
    val grid = this
    val beamLocationList = grid.rowList.flatten().filter { it.value.beamList.isNotEmpty() }
    if (beamLocationList.isEmpty()) {
        // Finished!
        return this
    } else {
        val location = beamLocationList[0]
        val image = location.value
        val beam = image.beamList[0]
        return when (image) {
            is Image.Mirror.Backward -> {
                TODO()
            }

            is Image.Mirror.Forward -> {
                TODO()
            }

            is Image.Space -> {
                when (beam.direction) {
                    Direction.Up -> {
                        grid.move(
                            fromRow = location.row,
                            fromColumn = location.column,
                            toRow = location.row - 1,
                            toColumn = location.column,
                            filledValue = Image.Space(energized = true, beamList = image.beamList.remove(beam)),
                            replaceValue = { oldValue: Image ->
                                oldValue.update(energized = true, beamList = oldValue.beamList + beam)
                            }
                        )
                    }

                    Direction.Right -> {
                        grid.move(
                            fromRow = location.row,
                            fromColumn = location.column,
                            toRow = location.row,
                            toColumn = location.column + 1,
                            filledValue = Image.Space(energized = true, beamList = image.beamList.remove(beam)),
                            replaceValue = { oldValue: Image ->
                                oldValue.update(energized = true, beamList = oldValue.beamList + beam)
                            }
                        )
                    }

                    Direction.Down -> {
                        grid.move(
                            fromRow = location.row,
                            fromColumn = location.column,
                            toRow = location.row + 1,
                            toColumn = location.column,
                            filledValue = Image.Space(energized = true, beamList = image.beamList.remove(beam)),
                            replaceValue = { oldValue: Image ->
                                oldValue.update(energized = true, beamList = oldValue.beamList + beam)
                            }
                        )
                    }

                    Direction.Left -> {
                        grid.move(
                            fromRow = location.row,
                            fromColumn = location.column,
                            toRow = location.row,
                            toColumn = location.column - 1,
                            filledValue = Image.Space(energized = true, beamList = image.beamList.remove(beam)),
                            replaceValue = { oldValue: Image ->
                                oldValue.update(energized = true, beamList = oldValue.beamList + beam)
                            }
                        )
                    }
                }
            }

            is Image.Splitter.Horizontal -> {
                TODO()
            }

            is Image.Splitter.Vertical -> {
                TODO()
            }
        }
    }
    return this
}

// ---------
sealed class Image {
    abstract val char: Char
    abstract val energized: Boolean
    abstract val beamList: List<Beam>

    abstract fun update(energized: Boolean, beamList: List<Beam>): Image

    sealed class Splitter(
        override val char: Char,
        override val energized: Boolean,
        override val beamList: List<Beam>
    ) : Image() {
        data class Vertical(
            override val energized: Boolean,
            override val beamList: List<Beam>
        ) : Splitter('|', energized, beamList) {
            override fun update(energized: Boolean, beamList: List<Beam>): Image =
                copy(energized = energized, beamList = beamList)

            override fun toString(): String = toPrintableString()
        }

        data class Horizontal(
            override val energized: Boolean,
            override val beamList: List<Beam>
        ) : Splitter('-', energized, beamList) {
            override fun update(energized: Boolean, beamList: List<Beam>): Image =
                copy(energized = energized, beamList = beamList)

            override fun toString(): String = toPrintableString()
        }
    }

    sealed class Mirror(
        override val char: Char,
        override val energized: Boolean,
        override val beamList: List<Beam>
    ) : Image() {
        data class Forward(
            override val energized: Boolean,
            override val beamList: List<Beam>
        ) : Mirror('/', energized, beamList) {
            override fun update(energized: Boolean, beamList: List<Beam>): Image =
                copy(energized = energized, beamList = beamList)

            override fun toString(): String = toPrintableString()
        }

        data class Backward(
            override val energized: Boolean,
            override val beamList: List<Beam>
        ) : Mirror('\\', energized, beamList) {
            override fun update(energized: Boolean, beamList: List<Beam>): Image =
                copy(energized = energized, beamList = beamList)

            override fun toString(): String = toPrintableString()
        }
    }

    data class Space(
        override val energized: Boolean,
        override val beamList: List<Beam>
    ) : Image() {
        override val char = '.'

        override fun update(energized: Boolean, beamList: List<Beam>): Image =
            copy(energized = energized, beamList = beamList)

        override fun toString(): String = toPrintableString()
    }

    fun toPrintableString(): String = when {
        beamList.isNotEmpty() -> {
            beamList.singleOrNull()?.direction?.char?.toString()
                ?: beamList.size.toString().last().toString()
        }
        this is Splitter || this is Mirror -> char.toString()
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
        '|' -> Image.Splitter.Vertical(energized = false, beamList = emptyList())
        '-' -> Image.Splitter.Horizontal(energized = false, beamList = emptyList())
        '/' -> Image.Mirror.Forward(energized = false, beamList = emptyList())
        '\\' -> Image.Mirror.Backward(energized = false, beamList = emptyList())
        '.' -> Image.Space(energized = false, beamList = emptyList())
        else -> error("Invalid image char: $char")
    }
