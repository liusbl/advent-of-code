package _2024.day05.initial

import java.io.File

fun main() {
    // Started: 2024-12-05 8:50
    // Finished: 2024-12-05 9:15
    // Solution: 5651
//    solvePart1()

    // Started: 2024-12-05 9:25
    // Finished: 2024-12-05 10:08
    // Solution: 4743
    solvePart2_comparatorApproach()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day05/input/input.txt")
    val lines = input.readLines()

    val (rules, updates) = separateRulesAndUpdates(lines)

    val correctUpdates = filterCorrectUpdates(rules, updates)

    val sum = getMiddleSum(correctUpdates)

    println(sum)
}

fun solvePart2_comparatorApproach() {
    val input = File("src/jvmMain/kotlin/day05/input/input.txt")
    val lines = input.readLines()

    val (rules, updates) = separateRulesAndUpdates(lines)

    val comparator = Comparator<String> { left, right ->
        if (rules.contains("$left|$right")) -1 else 0
    }

    val updateLines = updates.map { it.split(",") }

    // SO EASY AND IT JUST WORKS
    val incorrectUpdates = updateLines.map { update ->
        val sorted = update.sortedWith(comparator)
        update to sorted
    }.filter { it.first != it.second }.map { it.second.joinToString(",") }

    val sum = getMiddleSum(incorrectUpdates)

    println(sum)
}


fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day05/input/input_part1_test.txt")
    val lines = input.readLines()

    val (rules, updates) = separateRulesAndUpdates(lines)

    val comparator = object : Comparator<String> {
        override fun compare(o1: String?, o2: String?): Int {
            val contains = rules.contains("$o1|$o2")
            return if (contains) {
                -1
            } else {
                0
            }
        }
    }

    updates.forEach { update ->
        val sorted = update.split(",").sortedWith(comparator)
        println("Original: $update, sorted: $sorted")
    }

    val incorrectUpdates = fixIncorrectUpdates(rules, updates)

    val sum = getMiddleSum(incorrectUpdates)

    println(sum)
}

//fun a() {
//    val pairs = listOf(97 to 13, 47 to 13)
//    val res = listOf<Int>(97, 13, 75, 29, 47).sortedWith(Comparator<Int> { o1, o2 ->
//        val pair = pairs.find {
//            (it.first == o1 && it.second == o2) || (it.first == o2 && it.second == o1)
//        }
//        if (pair == null) {
//            0
//        } else {
//            if (pair.first == o1) {
//                -1
//            } else {
//                1
//            }
//        }
//    })
//
//    println(res)
//}

fun <T> List<T>.swap(firstIndex: Int, secondIndex: Int): List<T> {
    val secondValue = this[secondIndex]
    val firstValue = this[firstIndex]
    val mutableList = this.toMutableList()
    mutableList[secondIndex] = firstValue
    mutableList[firstIndex] = secondValue
    return mutableList
}

private fun fixIncorrectUpdates(rules: List<String>, updates: List<String>): List<String> {
    val pairList = rules.map {
        val (left, right) = it.split("|")
        left to right
    }

    val incorrectUpdates = updates.map { it.split(",") }
        .mapNotNull { update ->
            var newUpdate = update
            pairList.forEach { pair ->
                val firstIndex = newUpdate.indexOf(pair.first).takeIf { it != -1 }
                val secondIndex = newUpdate.indexOf(pair.second).takeIf { it != -1 }

                if (firstIndex != null && secondIndex != null && secondIndex < firstIndex) {
                    newUpdate = newUpdate.swap(firstIndex, secondIndex)
                }
            }

            println("newUpdate: ${newUpdate}")

            if (newUpdate == update) {
                null
            } else {
                newUpdate
            }
        }
        .map { update ->
            var previousUpdate = update
            var newUpdate = update
            while (true) {
                pairList.forEach { pair ->
                    val firstIndex = newUpdate.indexOf(pair.first).takeIf { it != -1 }
                    val secondIndex = newUpdate.indexOf(pair.second).takeIf { it != -1 }

                    if (firstIndex != null && secondIndex != null && secondIndex < firstIndex) {
                        newUpdate = newUpdate.swap(firstIndex, secondIndex)
                    }
                }

                println("newUpdate #2: ${newUpdate}")

                if (newUpdate == previousUpdate) {
                    break
                } else {
                    previousUpdate = newUpdate
                }
            }
            newUpdate
        }


    return incorrectUpdates.map { it.joinToString(separator = ",") }
}

private fun separateRulesAndUpdates(lines: List<String>): Pair<List<String>, List<String>> {
    val rules = lines.takeWhile { it.isNotBlank() }
    val updates = lines.minus(rules.toSet()).filter { it.isNotBlank() }
    return rules to updates
}

private fun filterCorrectUpdates(rules: List<String>, updates: List<String>): List<String> {
    val pairList = rules.map {
        val (left, right) = it.split("|")
        left to right
    }

    val correctUpdates = updates.map { it.split(",") }.filter { update ->
        val containsIncorrectUpdate = pairList.any { pair ->
            val firstIndex = update.indexOf(pair.first).takeIf { it != -1 } ?: return@any false
            val secondIndex = update.indexOf(pair.second).takeIf { it != -1 } ?: return@any false

            secondIndex < firstIndex
        }
        !containsIncorrectUpdate
    }

    return correctUpdates.map { it.joinToString(separator = ",") }
}

private fun getMiddleSum(updates: List<String>): Int =
    updates.sumOf {
        val list = it.split(",")
        val index = (list.size - 1) / 2
        list[index].toInt()
    }

private fun testAll() {
    test1()
    test2()
}

private fun test1() {
    val input = """
        1|2
        
        1,2,3,4,5
    """.trimIndent()

    val expected = listOf("1,2,3,4,5")

    test(input, expected)
}

private fun test2() {
    val input = """
        2|1
        
        1,2,3,4,5
    """.trimIndent()

    val expected = listOf<String>()

    test(input, expected)
}

private fun test(input: String, expected: List<String>) {
    val (rules, updates) = separateRulesAndUpdates(input.lines())
    val result = filterCorrectUpdates(rules, updates)

    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}