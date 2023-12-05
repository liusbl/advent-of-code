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

            acc.updateLast(category)
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

fun thing(category: Category, seed: Long): Pair<Boolean, Long>  {
    val firstNextSeed = category.list.find { it.contains(seed) }?.next(seed) ?: seed
    val firstKeyPoints = category.list.map { it.source to it.source + it.length }
    return firstKeyPoints.any { it.first == seed || it.second == seed } to firstNextSeed
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
            acc.updateLast { copy(length = next) }
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

            acc.updateLast(category)
        }
    }.map { category -> category.copy(list = category.list.sortedBy { map -> map.source }) }

//    val keyPoints = categoryList.map { it.list.map { it.source } }
//    println("Key points: ${keyPoints.take(2)}")

    println(categoryList.take(2).joinToString("\n"))

    /***** Experiments *////


    /**
     * Experiment 4
     */
    val listOfSeedsToCheck = listOf(82L).filter { seed ->
//    val listOfSeedsToCheck = (79..92L).filter { seed ->
        val category0 = categoryList[0]
        val (contains1, seed1) = thing(category0, seed)

        val category1 = categoryList[1]
        val (contains2, seed2) = thing(category1, seed1)

        val category2 = categoryList[2]
        val (contains3, seed3) = thing(category2, seed2)

        val category3 = categoryList[3]
        val (contains4, seed4) = thing(category3, seed3)

        val category4 = categoryList[4]
        val (contains5, seed5) = thing(category4, seed4)

        val category5 = categoryList[5]
        val (contains6, seed6) = thing(category5, seed5)

        val category6 = categoryList[6]
        val (contains7, seed7) = thing(category6, seed6)
        println(category6)

        println("$seed -> $seed1 -> $seed2 -> $seed3 -> $seed4 -> $seed5 -> $seed6 -> $seed7")
        println("$contains1 $contains2 $contains3 $contains4 $contains5 $contains6 $contains7")
        println()
        true
    }

    println(listOfSeedsToCheck)


    /**
     * Experiment 3
     */
//    val endSeed = 100L


//    val firstCategory = categoryList[0]
//    val firstKeyPoints = firstCategory.list.map { it.source to it.source + it.length }
//
//    val secondCategory = categoryList[1]
////    val secondKeyPoints = secondCategory.list.map { it.source to it.source + it.length }
//
//    var startSeed = 0L
//
//    while (startSeed <= endSeed) {
//        val firstNextSeed = firstCategory.list.find { it.contains(startSeed) }?.next(startSeed) ?: startSeed
//        val secondNextSeed = secondCategory.list.find { it.contains(firstNextSeed) }?.next(firstNextSeed) ?: firstNextSeed
//
//        println("startSeed: ${startSeed}")
//        println(
//            "firstCategory: ${firstCategory}\n" +
//                    "firstNextSeed: ${firstNextSeed}\n" +
//                    "firstKeyPoints: ${firstKeyPoints}\n" +
//                    "mapping: $startSeed to $firstNextSeed to $secondNextSeed"
//        )
//        println()
//        println(
//            "secondCategory: ${secondCategory}\n" +
//                    "secondNextSeed: ${secondNextSeed}\n" +
//                    "secondKeyPoints: ${secondKeyPoints}\n"
//        )
//        println("==================")
//
//        startSeed = firstKeyPoints.find { it.first > startSeed }?.first!!
//    }

    /**
     * Experiment 2
     */

//    val endSeed = 100L
//
//    val firstCategory = categoryList[0]
//    val firstKeyPoints = firstCategory.list.map { it.source to it.source + it.length }
//
//    val secondCategory = categoryList[1]
//    val secondKeyPoints = secondCategory.list.map { it.source to it.source + it.length }
//
//    var startSeed = 0L
//
//    while (startSeed <= endSeed) {
//        val firstNextSeed = firstCategory.list.find { it.contains(startSeed) }?.next(startSeed) ?: startSeed
//        val secondNextSeed = secondCategory.list.find { it.contains(firstNextSeed) }?.next(firstNextSeed) ?: firstNextSeed
//
//        println("startSeed: ${startSeed}")
//        println(
//            "firstCategory: ${firstCategory}\n" +
//                    "firstNextSeed: ${firstNextSeed}\n" +
//                    "firstKeyPoints: ${firstKeyPoints}\n" +
//                    "mapping: $startSeed to $firstNextSeed to $secondNextSeed"
//        )
//        println()
//        println(
//            "secondCategory: ${secondCategory}\n" +
//                    "secondNextSeed: ${secondNextSeed}\n" +
//                    "secondKeyPoints: ${secondKeyPoints}\n"
//        )
//        println("==================")
//
//        startSeed = firstKeyPoints.find { it.first > startSeed }?.first!!

//    /**
//     * Experiment 1
//     */
//    val listOfSeedsToCheck = (0..100L).filter { seed ->
//        val firstCategory = categoryList[0]
//        val firstNextSeed = firstCategory.list.find { it.contains(seed) }?.next(seed) ?: seed
//        val firstKeyPoints = firstCategory.list.map { it.source }
//        val firstEndPoints = firstCategory.list.map { it.source + it.length }
//
//        val secondCategory = categoryList[1]
//        val secondNextSeed = secondCategory.list.find { it.contains(firstNextSeed) }?.next(firstNextSeed)
//            ?: firstNextSeed
//        val secondKeyPoints = secondCategory.list.map { it.source }
//        val secondEndPoints = secondCategory.list.map { it.source + it.length }
//
//        println(
//            "$seed to $firstNextSeed to $secondNextSeed. " +
//                    "In List 1: ${firstKeyPoints.contains(seed).display()}. " +
//                    "In List 2: ${secondKeyPoints.contains(firstNextSeed).display()}. " +
//                    "In Endpoint 1: ${firstEndPoints.contains(seed).display()}. " +
//                    "In Endpoint 2: ${secondEndPoints.contains(firstNextSeed).display()}. "
//        )
//        firstKeyPoints.contains(seed) ||
//            secondKeyPoints.contains(firstNextSeed) ||
//            firstEndPoints.contains(seed) ||
//            secondEndPoints.contains(firstNextSeed)
//    }
//
//    println(listOfSeedsToCheck)

}

fun Boolean.display(): String {
    return if (this) "_TRUE" else "false"
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

fun <T> List<T>.updateLast(value: T): List<T> {
    return this.dropLast(1) + listOf(value)
}

fun <T> List<T>.updateLast(update: T.() -> T): List<T> {
    return updateLast(update(this.last()))
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
        return seed >= source && seed <= source + length - 1
    }

    fun next(seed: Long): Long =
        if (contains(seed)) {
            val delta = seed - source
            destination + delta
        } else {
            seed
        }
}