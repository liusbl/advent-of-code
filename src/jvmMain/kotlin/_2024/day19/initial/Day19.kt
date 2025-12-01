package _2024.day19.initial

import java.io.File

fun main() {
    // Started: 2024-12-19 11:30
    // Finished:
    // Solution:
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day19/input/input_part1_test.txt")
    val lines = input.readLines()

    val possibilities = lines[0].split(", ")

//    val patterns = listOf("bwurrg")
    val patterns = lines.drop(2)


    fun iterate(shortenedPattern: String) {
        println("iterate: $shortenedPattern")
        val validList = possibilities.filter { shortenedPattern.startsWith(it) }
        println("validList: $validList")
        if (shortenedPattern.isBlank()) {
            println("blank")
            return
        } else if (validList.isEmpty()) {
            println("empty list")
        } else {
            validList.forEach {
                iterate(shortenedPattern.drop(it.length))
            }
        }
    }

    patterns.sumOf {
        println()
        val result = iterate(it)
        println("Result: $result")
        result
        0L
    }.let(::println)

//    val count = patterns.count { pattern ->
//        var shortenedPattern = pattern
//        println("-----")
//        while (true) {
//            println(shortenedPattern)
//            if (shortenedPattern.isBlank()) return@count true
//            val found = possibilities.find { shortenedPattern.startsWith(it) } ?: return@count false
//            shortenedPattern = shortenedPattern.drop(found.length)
//        }
//        error("")
//    }
//    println(count)
}

/**
 * aaaa
 * aaab
 * aaac
 * aaba
 * ...
 */
//private fun <T> permutateWithRepetitions_fold(list: List<T>, size: Int): List<List<T>> {
//    // TODO
//}

/**
 * aaaa
 * aaab
 * aaac
 * aaba
 * ...
 */
private fun <T> permutateWithRepetitions_recursion(list: List<T>, size: Int): List<List<T>> {
    fun iterate(valueList: List<T>, count: Int): List<List<T>> =
        list.flatMap { value ->
            if (count >= size) {
                return listOf(valueList)
            } else {
                iterate(valueList + listOf(value), count + 1)
            }
        }

    return iterate(listOf(), 0)
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day19/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}