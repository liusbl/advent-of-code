package day03.initial

import java.io.File

fun main() {
    // Started: 2025-12-04 12:30
    // Finished: 2025-12-04 12:41
    // Solution: 17316
//    solvePart1()

    // Started: 2025-12-04 12:41
    // Finished: 2025-12-04 13:07
    // Solution: 171741365473332
    solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day03/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = lines.map { it.map { it.digitToInt() } }.sumOf { line ->
        val maxElement = line.withIndex().maxBy {
            if (it.index == line.size - 1) {
                -1
            } else {
                it.value
            }
        }.value
        val indexOfMax = line.indexOf(maxElement)
        val rightSide = line.subList(indexOfMax + 1, line.size)
        val secondMaxElement = rightSide.max()
        val resultString = "$maxElement$secondMaxElement"
        println(resultString)
        resultString.toInt()
    }

    println(result)
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day03/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = lines.map { it.map { it.digitToInt() } }.sumOf { line ->
        var result = ""

        var subLine = line

        while (true) {
            val firstMax = subLine.withIndex().maxBy { (index, value) ->
                // Still take the largest first number, unless there is not enough space at the  end.
                // For example: 234234234234278
                // We could take 8, but it's too far to the right
                // We could take 7, but it's too far
                // We could take 4, so we take it.

                // Need 4 digits
                // 141341

                val digitsLeft = 12 - result.length

                if (index + digitsLeft > subLine.size) {
                    -1
                } else {
                    value
                }
            }
            result += firstMax.value

            if (subLine.isEmpty() || result.length == 12) {
                break
            }
            subLine = subLine.subList(firstMax.index + 1, subLine.size)

        }
        println(result)
        result.toLong()
    }

    println(result)
}