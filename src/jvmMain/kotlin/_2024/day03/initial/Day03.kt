package day03.initial

import java.io.File

fun main() {
    // Started: 2024-12-03 11:06
    // Finished: 2024-12-03 11:22
    // Solution: 170778545
//    solvePart1()

    // Started: 2024-12-03 11:32
    // Finished:  2024-12-03 11:41
    // Solution: 82868252
    solvePart2()
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day03/input/input_part2_test.txt")
    val line = input.readLines().joinToString("")

    val matcher = "(mul\\((\\d+),(\\d+)\\))|(don't\\(\\))|(do\\(\\))".toRegex()

    val matchList = matcher.findAll(line)

    var enabled = true

    val result = matchList.sumOf { match ->
        println("--------")
        println("Match groups: ${match.groups}")
        println("Match.value: ${match.value}")
        val count = when (match.value) {
            "do()" -> {
                enabled = true
                0
            }
            "don't()" -> {
                enabled = false
                0
            }
            else -> {
                if (enabled) {
                    val first = match.groups[2]?.value?.toInt()!!
                    val second = match.groups[3]?.value?.toInt()!!
                    first * second
                } else {
                    0
                }
            }
        }
        println("Match.value: ${match.value}. Count: $count")
        count
    }

    println(result)
}

// 31705549: That's not the right answer; your answer is too low.
//  Reason: I wasn't taking all the lines
fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day03/input/input.txt")
    val line = input.readLines().joinToString("")

    val matcher = "(mul\\((\\d+),(\\d+)\\))|(don't\\(\\))|(do\\(\\))".toRegex()

    val matchList = matcher.findAll(line)
    val result = matchList.sumOf { match ->
//        val first = match.groups[1]?.value?.toInt()!!
//        val second = match.groups[2]?.value?.toInt()!!
//        first * second
        println(match.groups)
        1L
    }

    println(result)
}