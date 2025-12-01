package _2024.day11.initial

import java.math.BigInteger

fun main() {
    // Started: 2024-12-17 14:00
    // Finished: 2024-12-17 14:05
    // Solution: 186175
//    solvePart1()

    // COULD NOT SOLVE PART 2 SO CHECKED SOLUTIONS
    // Started: 2024-07-
    // Finished:
    // Solution:
    solvePart2()
}

val cache = mutableMapOf<Pair<Long, Int>, Long>()

fun solvePart2() {
    println("Start")
    println(listOf(125L,17L).sumOf {
        calculateCount(it, 10)
    })
}

fun blink(stone: Long): List<Long> = when {
    stone == 0L -> listOf(1)
    stone.toString().length % 2 == 0 -> {
        val whole = stone.toString()
        listOf(whole.take(whole.length / 2).toLong(), whole.drop(whole.length / 2).toLong())
    }
    else -> listOf(stone * 2024)
}

fun calculateCount(stone: Long, iterations: Int): Long {
    println("stone: $stone, iterations: $iterations, cacheSize: ${cache.size}, cache: $cache")
    if (iterations == 0) return 1
    cache[stone to iterations]?.let { return it }
    return blink(stone).sumOf { calculateCount(it, iterations - 1) }
        .also { cache[stone to iterations] = it }
        .also {
            println("calculateCount result: ${it}")
        }
}

//fun solvePart2() {
////    var list = listOf(5688L, 62084L, 2L, 3248809L, 179L, 79L, 0L, 172169L).map { BigInteger.valueOf(it) }
//    var list = listOf(125L,17L).map { BigInteger.valueOf(it) }
//    (1..75).forEach {
//        println("list: $list")
//        val res = list.fold(0) { acc, next ->
//            println("next: $next, acc: $acc")
//            acc + when {
//                next == BigInteger.ZERO -> {
//                    listOf(BigInteger.valueOf(1L))
//                }
//
//                next.toString().length % 2L == 0L -> {
//                    val str = next.toString()
//                    val left = BigInteger(str.take(str.length / 2))
//                    val right = BigInteger(str.takeLast(str.length / 2))
//                    listOf(left, right)
//                }
//
//                else -> listOf(next * BigInteger.valueOf(2024L))
//            }.size
//        }
//        println(res)
//        list = res
//        println()
//    }
//}



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
