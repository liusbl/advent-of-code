package day02.initial

import java.io.File
import kotlin.math.ceil

fun main() {
    // Started: 2025-12-02 14:45
    // Finished: 2025-12-02 14:55
    // Solution: 19128774598
//    solvePart1()

    // Started: 2025-12-02 14:55
    // Finished: 2025-12-02 15:17
    // WRONG Solution: 23605129900 is too high
    // WRONG Solution: 21932258689 is too high
    // WRONG Solution: 21917419849 is too low
    // Solution: 21932258645
    solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day02/input/input_part1_test.txt")
    val lines = input.readLines()[0].split(",")

    var sum = 0L

    lines.forEach { line ->
        val (start, end) = line.split("-").map { it.toLong() }

        val invalidNumbers = (start..end).mapNotNull { id ->
            val idString = id.toString()
            if (idString.length % 2 != 0) {
                null // Example: 101, 3
            } else {
                if (idString.take(idString.length / 2) == idString.takeLast(idString.length / 2)) {
                    id
                } else {
                    null
                }
            }
        }

        sum += invalidNumbers.sum()
        println("Start: $start, end: $end, invalid count: ${invalidNumbers.count()}")
    }

    val result = sum

    println(result)
}

fun solvePart2() {
//    val input = File(/* pathname = */ "src/jvmMain/kotlin/day02/input/input_part1_test.txt")
    val input = File(/* pathname = */ "src/jvmMain/kotlin/day02/input/input.txt")
    val lines = input.readLines()[0].split(",")

    var sum = 0L

    lines.forEach { line ->
        val (start, end) = line.split("-").map { it.toLong() }

        val invalidNumbers = (start..end).mapNotNull { id ->
            val idString = id.toString()

            if (id < 10) {
                return@mapNotNull null
            }

            val isInvalid = (1..ceil(idString.length.toFloat() / 2).toInt()).any { length ->
                var repeatedString = ""

                while (true) {
                    repeatedString += idString.take(length)
                    if (repeatedString.length >= idString.length) {
                        break
                    }
                }

                (repeatedString == idString).also {
                    if (it) {
                        println("Found invalid number: $idString")
                    }
                }


//                val repeatedString = idString.take(length).repeat(idString.length / length)

//                (repeatedString.take(idString.length) == idString).also {
//                    if (it) {
//                        println("Found invalid number: $idString")
//                    }
//                }
            }

            if (isInvalid) id else null
        }

        sum += invalidNumbers.sum()
        println("Start: $start, end: $end, invalid count: ${invalidNumbers.count()}")
        println()
    }

    val result = sum

    println(result)
}