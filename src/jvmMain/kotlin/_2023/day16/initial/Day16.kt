package _2023.day16.initial

import _2023.day11.initial.*
import util.remove
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

    val initialBeamGrid = grid.update(0, 0) { image ->
        image.update(energized = true, beamList = listOf(Beam(Direction.Right)))
    }

    println("Initial beam:")
    println(initialBeamGrid.toPrintableString(includeLocation = false))
    println()

    var adv: Grid<Image> = initialBeamGrid
    var index = 0
    while(index < 50) {
        val newGrid = adv.advance()
        if (newGrid == null) {
            println("FINISHED:")
            println(adv.toPrintableString(includeLocation = false))
            println()
            println(adv.rowList.flatten().count { it.value.energized })
            break
        } else {
            adv = newGrid
            println("Advanced. Iteration: ${++index}")
            println(adv.toPrintableString(includeLocation = false))
            println()
        }
    }
}

fun Grid<Image>.advance(): Grid<Image>? {
    val grid = this
    val beamLocationList = grid.rowList.flatten().filter { it.value.beamList.isNotEmpty() }
    if (beamLocationList.isEmpty()) {
        // Finished!
        return null
    } else {
        val location = beamLocationList[0]
        val image = location.value
        val beam = image.beamList[0]

        fun moveUp(newDirection: Direction): Grid<Image> =
            grid.move(
                fromRow = location.row,
                fromColumn = location.column,
                toRow = location.row - 1,
                toColumn = location.column,
                filledValue = image.update(energized = true, beamList = image.beamList.remove(beam)),
                replaceValue = { oldValue: Image ->
                    oldValue.update(energized = true, beamList = oldValue.beamList + Beam(newDirection))
                }
            )

        fun moveRight(newDirection: Direction): Grid<Image> =
            grid.move(
                fromRow = location.row,
                fromColumn = location.column,
                toRow = location.row,
                toColumn = location.column + 1,
                filledValue = image.update(energized = true, beamList = image.beamList.remove(beam)),
                replaceValue = { oldValue: Image ->
                    oldValue.update(energized = true, beamList = oldValue.beamList + Beam(newDirection))
                }
            )

        fun moveDown(newDirection: Direction): Grid<Image> =
            grid.move(
                fromRow = location.row,
                fromColumn = location.column,
                toRow = location.row + 1,
                toColumn = location.column,
                filledValue = image.update(energized = true, beamList = image.beamList.remove(beam)),
                replaceValue = { oldValue: Image ->
                    oldValue.update(energized = true, beamList = oldValue.beamList + Beam(newDirection))
                }
            )

        fun moveLeft(newDirection: Direction): Grid<Image> =
            grid.move(
                fromRow = location.row,
                fromColumn = location.column,
                toRow = location.row,
                toColumn = location.column - 1,
                filledValue = image.update(energized = true, beamList = image.beamList.remove(beam)),
                replaceValue = { oldValue: Image ->
                    oldValue.update(energized = true, beamList = oldValue.beamList + Beam(newDirection))
                }
            )

        fun splitHorizontal(): Grid<Image> =
            moveLeft(Direction.Left)
                .update(
                    row = location.row,
                    column = location.column + 1,
                    transform = { oldValue ->
                        oldValue.update(energized = true, beamList = oldValue.beamList + Beam(Direction.Right))
                    }
                )

        fun splitVertical(): Grid<Image> =
            moveUp(Direction.Up)
                .update(
                    row = location.row + 1,
                    column = location.column,
                    transform = { oldValue ->
                        oldValue.update(energized = true, beamList = oldValue.beamList + Beam(Direction.Down))
                    }
                )

        return when (image) {
            is Image.Mirror.Backward -> {
                when (beam.direction) {
                    Direction.Up -> moveLeft(Direction.Left)
                    Direction.Right -> moveDown(Direction.Down)
                    Direction.Down -> moveRight(Direction.Right)
                    Direction.Left -> moveUp(Direction.Up)
                }
            }

            is Image.Mirror.Forward -> {
                when (beam.direction) {
                    Direction.Up -> moveRight(Direction.Right)
                    Direction.Right -> moveUp(Direction.Up)
                    Direction.Down -> moveLeft(Direction.Left)
                    Direction.Left -> moveDown(Direction.Down)
                }
            }

            is Image.Space -> {
                when (beam.direction) {
                    Direction.Up -> moveUp(Direction.Up)
                    Direction.Right -> moveRight(Direction.Right)
                    Direction.Down -> moveDown(Direction.Down)
                    Direction.Left -> moveLeft(Direction.Left)
                }
            }

            is Image.Splitter.Horizontal -> {
                when (beam.direction) {
                    Direction.Up -> splitHorizontal()
                    Direction.Right -> moveRight(Direction.Right)
                    Direction.Down -> splitHorizontal()
                    Direction.Left -> moveLeft(Direction.Left)
                }
            }

            is Image.Splitter.Vertical -> {
                when (beam.direction) {
                    Direction.Up -> moveUp(Direction.Up)
                    Direction.Right -> splitVertical()
                    Direction.Down -> moveDown(Direction.Down)
                    Direction.Left -> splitVertical()
                }

            }
        }
    }
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

    fun toPrintableString(): String {
        val a =  if (beamList.isNotEmpty()) {
            beamList.singleOrNull()?.direction?.char?.toString()
                ?: beamList.size.toString().last().toString()
        } else if (this is Splitter || this is Mirror) {
            char.toString()
        } else if (energized) {
            "E"
        } else {
            char.toString()
        }
        return a
    }
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
