package day15.initial

import java.io.File

fun main() {
    solvePart1() // Solution: 507769, time: 9:18
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day15/input/input_part2_test.txt")
    val input = File("src/jvmMain/kotlin/day15/input/input.txt")
    val lines = input.readLines()[0]

    val res = lines.split(",").map { value ->
        value.fold(0) { acc, next ->
             ((acc + next.code) * 17) % 256
        }
    }

    println(res.sum())
}