package day05.initial

import java.io.File

fun main() {
    // Overslept and started at 07:43
    solvePart1() // Solution: 340994526, at 08:49
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