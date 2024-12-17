package day11.initial

import java.io.File
import java.math.BigInteger

fun main() {
    // Started: 2024-12-17 14:00
    // Finished: 2024-12-17 14:05
    // Solution:186175
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    var list = listOf(
        5688L, 62084L, 2L, 3248809L, 179L, 79L, 0L,
        172169L
    ).map { BigInteger.valueOf(it) }
//    var list = listOf(125L,17L)
    (1..75).forEach {
        val res = list.fold(listOf<BigInteger>()) { acc, next ->
            acc + when {
                next == BigInteger.ZERO -> {
                    listOf(BigInteger.valueOf(1L))
                }

                next.toString().length % 2L == 0L -> {
                    val str = next.toString()
                    val left = BigInteger(str.take(str.length / 2))
                    val right = BigInteger(str.takeLast(str.length / 2))
                    listOf(left, right)
                }

                else -> listOf(next * BigInteger.valueOf(2024L))
            }
        }
        println(res.count())
        list = res
    }
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day11/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}