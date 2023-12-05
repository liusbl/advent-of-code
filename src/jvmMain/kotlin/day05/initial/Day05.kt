package day05.initial

import java.io.File

fun main() {
    // Overslept and started at 07:43
//    solvePart1() // Solution: 340994526, at 08:49
    solvePart2() // Solution:
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day05/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day05/input/input.txt")
    val lines = input.readLines()

    val seeds = lines[0].split(":")[1].trim().split(" ").map { it.toLong() }

    val categoryList = lines.drop(2).fold(emptyList<Category>()) { acc, next ->
        if (next.contains("map")) {
            acc.add(Category(emptyList()))
        } else if (next.isBlank()) {
            // complete category
            acc
        } else {
            val (destination, source, length) = next.split(" ").map { it.toLong() }
            val map = Map(destination = destination, source = source, length = length)

            val last = acc.last()

            val category = Category(last.list.add(map))

            acc.setLast(category)
        }
    }

    val min = seeds.minOfOrNull { seed ->
        val res = categoryList.fold(seed) { acc, category ->
            category.list.find { it.contains(acc) }?.next(acc) ?: acc
        }
        println(res)
        println()
        res
    }

    println("min: $min")
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day05/input/input_part1_test.txt")
//    val input = File("src/jvmMain/kotlin/day05/input/input.txt")
    val lines = input.readLines()

    val seedNumbers = lines[0].split(":")[1].trim().split(" ").map { it.toLong() }
    val seedList = seedNumbers.foldIndexed(emptyList<Seed>()) { index, acc, next ->
        if (index % 2 == 0) {
            acc.add(Seed(number = next, length = 0))
        } else {
            acc.setLast(acc.last().copy(length = next))
        }
    }

    val seeds = seedList.flatMap { List(it.length.toInt()) { index -> it.number + index } }

    println("Seeds: $seeds")

    val categoryList = lines.drop(2).fold(emptyList<Category>()) { acc, next ->
        if (next.contains("map")) {
            acc.add(Category(emptyList()))
        } else if (next.isBlank()) {
            // complete category
            acc
        } else {
            val (destination, source, length) = next.split(" ").map { it.toLong() }
            val map = Map(destination = destination, source = source, length = length)

            val last = acc.last()

            val category = Category(last.list.add(map))

            acc.setLast(category)
        }
    }.map { category -> category.copy(list = category.list.sortedBy { map -> map.source }) }

    val keyPoints = categoryList.map { it.list.map { it.source } }
    println("Key points: ${keyPoints.take(2)}")

    println(categoryList.joinToString("\n"))

    val min = (0..100L).minOfOrNull { seed ->
        val categorySubList = categoryList.take(1)
        val categoryKeyPoints = categorySubList.map { it.list.map { it.source } }.flatten()
        val res = categorySubList.fold(seed) { acc, category ->
            category.list.find { it.contains(acc) }?.next(acc) ?: acc
        }

        val categorySubList2 = listOf(categoryList[1])
        val categoryKeyPoints2 = categorySubList2.map { it.list.map { it.source } }.flatten()
        val res2 = categorySubList2.fold(res) { acc, category ->
            category.list.find { it.contains(acc) }?.next(acc) ?: acc
        }

        if (categoryKeyPoints.contains(seed) && categoryKeyPoints2.contains(seed)) {
            println("$seed to $res to $res2 <-- key point from list 1 AND list 2")
        } else if (categoryKeyPoints.contains(seed)) {
            println("$seed to $res to $res2 <-- key point from list 1")
        } else if (categoryKeyPoints2.contains(seed)) {
            println("$seed to $res to $res2 <-- key point from list 2")
        } else {
            println("$seed to $res to $res2")
        }

        res
    }

//    val min = seeds.minOfOrNull { seed ->
//        val res = categoryList.fold(seed) { acc, category ->
//            category.list.find { it.contains(acc) }?.next(acc) ?: acc
//        }
//        println(res)
//        println()
//        res
//    }

//    println("min: $min")
}

// Is there a way to combine categories so that I that instead of seed-to-soil to soil-to-fertilizer
//      we could directly do seed-to-fertilizer??
fun keyNumbersToCheck() {
    /**
     *
    seed-to-soil map:
    52 50 48
    50 98 2

    0 -> 0 <-- key point (unmarked)
    1 -> 1
    ..
    49 -> 49
    50 -> 52 <-- key point
    51 -> 53
    ..
    97 -> 99
    98 -> 50 <-- key point
    99 -> 51
    100 -> 100 <- key point (unmarked)
    100 -> 101
    ..

    =====

    soil-to-fertilizer map:
    39 0 15
    0 15 37
    37 52 2

    0 -> 39 <-- key point
    1 -> 40
    ..
    14 -> 53
    15 -> 0 <-- key point
    16 -> 1
    ..
    51 -> 36
    52 -> 37 <-- key point
    53 -> 38
    54 -> 54 <-- key point
    55 -> 55
    ..

    ==========
    COMBINATED seed-to-fertilizer map:
    0 -> 39 <-- key point
    1 -> 40
    ..


     */
}

fun <T> List<T>.setLast(value: T): List<T> {
    return this.dropLast(1) + listOf(value)
}

fun <T> List<T>.add(value: T): List<T> {
    return this + listOf(value)
}

data class Category(
    val list: List<Map>
) {
    fun next(seed: Long): Long {
        return list.fold(seed) { acc, map ->
            map.next(acc)
        }
//        list.find { map ->
//            map
//        } ?: Map(seed, seed, 1)
//    }
    }
}

data class Seed(
    val number: Long,
    val length: Long
)

data class Map(
    val destination: Long,
    val source: Long,
    val length: Long
) {
    fun contains(seed: Long): Boolean {
        return seed >= source && seed <= source + length
    }

    fun next(seed: Long): Long =
        if (contains(seed)) {
            val delta = seed - source
            destination + delta
        } else {
            seed
        }
}