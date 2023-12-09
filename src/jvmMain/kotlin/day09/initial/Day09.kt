package day09.initial

import day05.initial.add
import util.set
import java.io.File

fun main() {
    solvePart1() // Solution: 2175229206, time: 09:56

}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day09/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day09/input/input.txt")
    val lines = input.readLines()

    val numberLines = lines.map { it.split(" ").map { it.trim().toLong() } }

    val result = numberLines.sumOf { line ->
        println(line.joinToString(" "))
        var newLineList = listOf(line)
        while (!newLineList.last().all { it == 0L }) {
            val newLine = newLineList.last().zipWithNext { first, second ->
                second - first
            }
            newLineList = newLineList.add(newLine)
            println(newLine)
        }
        println(newLineList)

        val reversed = newLineList.reversed()

        val updatedList = reversed.foldIndexed(reversed) { index, acc, list ->
            if (index == 0) {
                acc.set(index, list.add(0))
            } else {
                acc.set(index, list.add(list.last() + acc[index - 1].last()))
            }
        }

        println(updatedList)

        println()

        updatedList.last().last()
    }

    println(result)
}