package _2024.day08.initial

import util.set
import java.io.File

fun main() {
    // Started: 2024-12-11 22:15
    // Finished: 2024-12-11 23:30
    // Solution: 367
//    solvePart1()

    // Started: 2024-12-11 23:30
    // Finished:  2024-12-11 23:36
    // Solution: 1285
    solvePart2()
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day08/input/input.txt")
    val lines = input.readLines()

    val pointRows = lines.mapIndexed { lineIndex, line ->
        line.mapIndexed { letterIndex, letter ->
            Point(lineIndex, letterIndex, letter)
        }
    }
    val points = pointRows.flatten()

    val antennaPoints = points.filter { it.letter != '.' }

    val antennaPairs = mutableSetOf<Pair<Point, Point>>()
    antennaPoints.forEach { point1 ->
        antennaPoints.forEach { point2 ->
            if (point1 != point2 && point1.letter == point2.letter) {
                antennaPairs.add(point1 to point2)
            }
        }
    }

    var newPointRows = List(pointRows.size) { lineIndex ->
        List(pointRows[0].size) { letterIndex -> Point(lineIndex, letterIndex, '.') }
    }

    antennaPairs.forEach { (point1, point2) ->
        try {
            var multiplier = 0
            while (true) {
                val lineIndexDifference = point2.lineIndex - point1.lineIndex
                val newLineIndex = point1.lineIndex - lineIndexDifference * multiplier

                val letterIndexDifference = point2.letterIndex - point1.letterIndex
                val newLetterIndex = point1.letterIndex - letterIndexDifference * multiplier

                newPointRows = newPointRows.set(newLineIndex, newLetterIndex, { it.copy(letter = '#') })

                println("$point1\n$point2\nnewLineIndex:$newLineIndex,newLetterIndex:$newLetterIndex")
                println(newPointRows.toDisplayString())
                println("--------------------")
                println()
                multiplier++
            }
        } catch (cause: Exception) {
            println("Antinode outside grid, skipping")
        }
    }

    println(newPointRows.toDisplayString())

    println(newPointRows.flatten().filter { it.letter != '.' }.size)
}

fun List<List<Point>>.toDisplayString(): String =
    this.joinToString(separator = "\n", transform = { row ->
        row.joinToString(separator = "", transform = { point -> point.toDisplayString() })
    })

data class Point(
    val lineIndex: Int,
    val letterIndex: Int,
    val letter: Char
) {
    fun toDisplayString(): String {
        return "$letter"
    }
}

interface IndexUpdater {
    fun update(lines: List<String>, point: Point): Point?

    operator fun plus(updater: IndexUpdater): IndexUpdater {
        return object : IndexUpdater {
            override fun update(lines: List<String>, point: Point): Point? {
                val newPoint = this@IndexUpdater.update(lines, point) ?: return null
                return updater.update(lines, newPoint)
            }
        }
    }

    object Up : IndexUpdater {
        override fun update(lines: List<String>, point: Point): Point? =
            point.copy(lineIndex = point.lineIndex - 1).takeIf { it.lineIndex >= 0 }
    }

    object Right : IndexUpdater {
        override fun update(lines: List<String>, point: Point): Point? =
            point.copy(letterIndex = point.letterIndex + 1).takeIf { it.letterIndex < lines[0].length }
    }

    object Down : IndexUpdater {
        override fun update(lines: List<String>, point: Point): Point? =
            point.copy(lineIndex = point.lineIndex + 1).takeIf { it.lineIndex < lines.size }
    }

    object Left : IndexUpdater {
        override fun update(lines: List<String>, point: Point): Point? =
            point.copy(letterIndex = point.letterIndex - 1).takeIf { it.letterIndex >= 0 }
    }

    companion object {
        fun all() = orthogonal() + diagonal()

        fun diagonal() = listOf(
            Up + Right,
            Down + Right,
            Down + Left,
            Up + Left
        )

        fun orthogonal() = listOf(
            Up,
            Right,
            Down,
            Left
        )
    }
}

fun IndexUpdater.nextOrthogonalClockwise(): IndexUpdater {
    val list = IndexUpdater.orthogonal()
    return IndexUpdater.orthogonal()[(list.indexOf(this) + 1) % list.size]
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day08/input/input.txt")
    val lines = input.readLines()

    val pointRows = lines.mapIndexed { lineIndex, line ->
        line.mapIndexed { letterIndex, letter ->
            Point(lineIndex, letterIndex, letter)
        }
    }
    val points = pointRows.flatten()

    val antennaPoints = points.filter { it.letter != '.' }

    val antennaPairs = mutableSetOf<Pair<Point, Point>>()
    antennaPoints.forEach { point1 ->
        antennaPoints.forEach { point2 ->
            if (point1 != point2 && point1.letter == point2.letter) {
                antennaPairs.add(point1 to point2)
            }
        }
    }

    var newPointRows = List(pointRows.size) { lineIndex ->
        List(pointRows[0].size) { letterIndex -> Point(lineIndex, letterIndex, '.') }
    }

    antennaPairs.forEach { (point1, point2) ->
        try {
            val lineIndexDifference = point2.lineIndex - point1.lineIndex
            val newLineIndex = point1.lineIndex - lineIndexDifference

            val letterIndexDifference = point2.letterIndex - point1.letterIndex
            val newLetterIndex = point1.letterIndex - letterIndexDifference

            newPointRows = newPointRows.set(newLineIndex, newLetterIndex, { it.copy(letter = '#') })

            println("$point1\n$point2\nnewLineIndex:$newLineIndex,newLetterIndex:$newLetterIndex")
            println(newPointRows.toDisplayString())
            println("--------------------")
            println()
        } catch (cause: Exception) {
            println("Antinode outside grid, skipping")
        }
    }

    println(newPointRows.toDisplayString())

    println(newPointRows.flatten().filter { it.letter != '.' }.size)
}
