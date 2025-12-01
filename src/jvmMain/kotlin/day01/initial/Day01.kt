package day01.initial

import java.io.File

fun main() {
    // Started: 2025-12-01 11:00
    // Finished: 2025-12-01 11:06
    // Solution: 1097
//    solvePart1()

    // Started: 2025-12-01 11:06
    // Finished: 2025-12-01 11:35
    // TOO LOW solution: 2731
    // TOO HIGH solution: 7587
    // Solution: 7101
    solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day01/input/input.txt")
    val lines = input.readLines()

    var sum = 10000050

    var code = 0

    lines.forEach {
        if (it.startsWith("L")) {
            sum += it.drop(1).toInt()
        } else {
            sum -= it.drop(1).toInt()
        }

        println(sum)

        if (sum.toString().takeLast(2) == "00") {
            code++
        }
    }

    val result = code

    println(result)
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day01/input/input.txt")
    val lines = input.readLines()

    var code = 0
    var sum = 10000050

    lines.forEach {
        val count = it.drop(1).toInt()

        if (it.startsWith("L")) {
            repeat(count) {
                sum++
                if (sum.toString().takeLast(2) == "00") {
                    code++
                }
            }
        } else {
            repeat(count) {
                sum--
                if (sum.toString().takeLast(2) == "00") {
                    code++
                }
            }
        }
    }

    val result = code

    println(result)
}

fun solvePart2_wrong() {
    val input = File("src/jvmMain/kotlin/day01/input/input.txt")
    val lines = input.readLines()

    var sum = 10000050

    var code = 0


    lines.forEach {
        val previousSum = sum

        val count = it.drop(1)

        if (it.startsWith("L")) {
            sum += count.toInt()
        } else {
            sum -= count.toInt()
        }

        println(sum)

        if (previousSum.toString().dropLast(2) != sum.toString().dropLast(2)) {
            code++

            if (count.toInt() > 99) {
                code += count.toString().take(1).toInt()
            }
        }
    }

    val result = code

    println(result)
}