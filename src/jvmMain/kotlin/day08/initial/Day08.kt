package day08.initial

import java.io.File

fun main() {
    // Started:
    // Finished:
    // Solution:
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day08/input/input_part1_test.txt")
    val lines = input.readLines()

    val pointRows = lines.mapIndexed { lineIndex, line ->
        line.mapIndexed { letterIndex, letter ->
            Point(lineIndex, letterIndex, letter)
        }
    }
    val points = pointRows.flatten()

    val antennaPoints = points.filter { it.letter == 'a' }

    val antennaPairs = mutableSetOf<Pair<Point,Point>>()
    antennaPoints.forEach { point1 ->
        antennaPoints.forEach { point2 ->
            if (point1 != point2 && !antennaPairs.contains(point2 to point1)) {
                antennaPairs.add(point1 to point2)
            }
        }
    }



    println(antennaPairs.joinToString("\n"))
}

data class Point(
    val lineIndex: Int,
    val letterIndex: Int,
    val letter: Char
)

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

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day08/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}