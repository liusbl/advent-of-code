package day06.initial

import util.set
import java.io.File

fun main() {
    // Started: 2024-12-06 10:15
    // Finished: 2024-12-06 10:45
    // Solution: 5129
//    solvePart1()

    // Started:
    // Finished:
    // Solution:
    solvePart2()
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day06/input/input.txt")
    var lines = input.readLines()

    val initialLine = lines.first { line -> line.contains('^') }
    val initialLineIndex = lines.indexOf(initialLine)
    val initialLetterIndex = initialLine.indexOf('^')

    lines = lines.set(initialLineIndex, lines[initialLineIndex].replace('^', '.'))

    var point: Point? = Point(initialLineIndex, initialLetterIndex)
    var indexUpdater: IndexUpdater = IndexUpdater.Up

    val pointSet = mutableSetOf<Pair<Point, IndexUpdater>>()
    pointSet.add(point!! to indexUpdater)

    while (true) {
        val newPoint = indexUpdater.update(lines, point!!)
        println("Checking point: ${point.lineIndex},${point.letterIndex}, direction: $indexUpdater")
        if (newPoint == null) {
            // Left the grid, finish
            break
        } else {
            if (lines[newPoint.lineIndex][newPoint.letterIndex] != '.') {
                indexUpdater = indexUpdater.nextOrthogonalClockwise()
            } else {
                point = newPoint
                pointSet.add(point to indexUpdater)
            }
        }
    }

    val newLinesCollection = pointSet.drop(1).distinctBy { it.first }.map { it.first }
        .map { visitedPoint ->
            val newLines = lines.set(visitedPoint.lineIndex, lines[visitedPoint.lineIndex].toList().set(visitedPoint.letterIndex, 'O').joinToString(separator = ""))
            println(newLines.joinToString(separator = "\n"))
            println()
            newLines
        }

    var count = 0

    newLinesCollection.forEach { newLines ->
        println("---------------------")
        println("Checking new obstruction")
        println(newLines.joinToString(separator = "\n"))

        var iu: IndexUpdater = IndexUpdater.Up
        var p: Point? = Point(initialLineIndex, initialLetterIndex)
        var np: Point? = p
        val s = mutableSetOf<Pair<Point, IndexUpdater>>()
        s.add(p!! to iu)

        while (true) {
            np = iu.update(newLines, p!!)
            println("Checking point: ${np?.lineIndex},${np?.letterIndex}, direction: $iu")
            if (np == null) {
                // Left the grid, finish
                println("Left the grid, finish")
                return@forEach
            } else {
                if (newLines[np.lineIndex][np.letterIndex] != '.') {
                    iu = iu.nextOrthogonalClockwise()
                } else {
                    p = np
                    if (s.contains(np to iu)) {
                        println("LOOP FOUND, YAY")
                        count++
                        return@forEach
                    }
                    s.add(p to iu)
                }
            }
        }
    }

//    println(pointSet.distinctBy { it.first }.size)
    println("FINAL COUNT: $count")
//    println(pointSet.distinctBy { it.first }.size)
}


data class Point(
    val lineIndex: Int,
    val letterIndex: Int
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


fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day06/input/input.txt")
    var lines = input.readLines()

    val initialLine = lines.first { line -> line.contains('^') }
    val initialLineIndex = lines.indexOf(initialLine)
    val initialLetterIndex = initialLine.indexOf('^')

    lines = lines.set(initialLineIndex, lines[initialLineIndex].replace('^', '.'))

    var point: Point? = Point(initialLineIndex, initialLetterIndex)
    var indexUpdater: IndexUpdater = IndexUpdater.Up

    val pointSet = mutableSetOf<Point>()
    pointSet.add(point!!)

    while (true) {
        val newPoint = indexUpdater.update(lines, point!!)
        println("Checking point: ${point.lineIndex},${point.letterIndex}")
        if (newPoint == null) {
            // Left the grid, finish
            break
        } else {
            if (lines[newPoint.lineIndex][newPoint.letterIndex] != '.') {
                indexUpdater = indexUpdater.nextOrthogonalClockwise()
            } else {
                point = newPoint
                pointSet.add(point)
            }
        }
    }

    println(pointSet.size)
}
