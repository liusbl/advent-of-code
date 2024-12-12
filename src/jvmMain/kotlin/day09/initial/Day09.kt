package day09.initial

import java.io.File

fun main() {
    // Started: 2024-12-12 14:20
    // Finished:
    // Solution:
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day09/input/input_part1_test.txt")
    val line = input.readLines()[0].map { it.digitToInt() }

    val parsedLine = line.foldIndexed(listOf<Block>()) { index, acc, digit ->
        acc + if (index % 2 == 0) {
            List(digit) { Block.File(index / 2) }
        } else {
            List(digit) { Block.Empty }
        }
    }

    val result = parsedLine.toDisplayString()

    println(result)
}

sealed interface Block {
    data class File(val id: Int) : Block

    object Empty : Block
}

fun List<Block>.toDisplayString(): String {
    return joinToString(separator = "", transform = { block ->
        when(block) {
            is Block.Empty -> "."
            is Block.File -> "${block.id}"
        }
    })
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day09/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}