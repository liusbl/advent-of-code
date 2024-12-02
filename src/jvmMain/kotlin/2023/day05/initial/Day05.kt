package day05.initial

import util.add
import util.updateLast
import java.io.File

fun main() {
    // Overslept and started at 07:43
//    solvePart1() // Solution: 340994526, at 08:49
    solvePart2() // Solution:
}

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

    val categoryList = lines.drop(2).fold(emptyList<Category>()) { acc, line ->
        if (line.contains("map")) {
            val name = line.split(" ")[0]
            acc.add(Category(name, emptyList()))
        } else if (line.isBlank()) {
            acc
        } else {
            val (destination, source, length) = line.split(" ").map { it.toLong() }
            val map = Map(destination = destination, source = source, length = length)
            acc.updateLast { copy(list = list.add(map)) }
        }
    }.map { category -> category.copy(list = category.list.sortedBy { map -> map.source }) }


    val seedCheckList = mutableListOf<Long>()
    (564468486L..(564468486+ 226119074)).forEach { startSeed0 ->
        val category0 = categoryList[0] // seed-to-soil
        val startSources0 = category0.list.map { it.source }
        val endSources0 = category0.list.map { it.source + it.length }
        val endSeed0 = category0.list.find { it.contains(startSeed0) }?.next(startSeed0) ?: startSeed0
        val inStartSources0 = startSources0.any { it == startSeed0 }
        val inEndSources0 = endSources0.any { it == startSeed0 }
        val inStartSourceText0 = if (inStartSources0) " StartSource0 " else ""
        val inEndSourceText0 = if (inEndSources0) " EndSource0 " else ""

        val category1 = categoryList[1] // soil-to-fertilizer
        val startSources1 = category1.list.map { it.source }
        val endSources1 = category1.list.map { it.source + it.length }
        val endSeed1 = category1.list.find { it.contains(endSeed0) }?.next(endSeed0) ?: endSeed0
        val inStartSources1 = startSources1.any { it == endSeed0 }
        val inEndSources1 = endSources1.any { it == endSeed0 }
        val inStartSourceText1 = if (inStartSources1) " StartSource1 " else ""
        val inEndSourceText1 = if (inEndSources1) " EndSource1 " else ""

        val category2 = categoryList[2] // fertilizer-to-water
        val startSources2 = category2.list.map { it.source }
        val endSources2 = category2.list.map { it.source + it.length }
        val endSeed2 = category2.list.find { it.contains(endSeed1) }?.next(endSeed1) ?: endSeed1
        val inStartSources2 = startSources2.any { it == endSeed1 }
        val inEndSources2 = endSources2.any { it == endSeed1 }
        val inStartSourceText2 = if (inStartSources2) " StartSource2 " else ""
        val inEndSourceText2 = if (inEndSources2) " EndSource2 " else ""

        val category3 = categoryList[3] // water-to-light
        val startSources3 = category3.list.map { it.source }
        val endSources3 = category3.list.map { it.source + it.length }
        val endSeed3 = category3.list.find { it.contains(endSeed2) }?.next(endSeed2) ?: endSeed2
        val inStartSources3 = startSources3.any { it == endSeed2 }
        val inEndSources3 = endSources3.any { it == endSeed2 }
        val inStartSourceText3 = if (inStartSources3) " StartSource3 " else ""
        val inEndSourceText3 = if (inEndSources3) " EndSource3 " else ""

        val category4 = categoryList[4] // light-to-temperature
        val startSources4 = category4.list.map { it.source }
        val endSources4 = category4.list.map { it.source + it.length }
        val endSeed4 = category4.list.find { it.contains(endSeed3) }?.next(endSeed3) ?: endSeed3
        val inStartSources4 = startSources4.any { it == endSeed3 }
        val inEndSources4 = endSources4.any { it == endSeed3 }
        val inStartSourceText4 = if (inStartSources4) " StartSource4 " else ""
        val inEndSourceText4 = if (inEndSources4) " EndSource4 " else ""

        val category5 = categoryList[5] // temperature-to-humidity
        val startSources5 = category5.list.map { it.source }
        val endSources5 = category5.list.map { it.source + it.length }
        val endSeed5 = category5.list.find { it.contains(endSeed4) }?.next(endSeed4) ?: endSeed4
        val inStartSources5 = startSources5.any { it == endSeed4 }
        val inEndSources5 = endSources5.any { it == endSeed4 }
        val inStartSourceText5 = if (inStartSources5) " StartSource5 " else ""
        val inEndSourceText5 = if (inEndSources5) " EndSource5 " else ""

        val category6 = categoryList[6] // humidity-to-location
        val startSources6 = category6.list.map { it.source }
        val endSources6 = category6.list.map { it.source + it.length }
        val endSeed6 = category6.list.find { it.contains(endSeed5) }?.next(endSeed5) ?: endSeed5
        val inStartSources6 = startSources6.any { it == endSeed5 }
        val inEndSources6 = endSources6.any { it == endSeed5 }
        val inStartSourceText6 = if (inStartSources6) " StartSource6 " else ""
        val inEndSourceText6 = if (inEndSources6) " EndSource6 " else ""

        println(
            "$startSeed0 -> $endSeed0 -> $endSeed1 -> $endSeed2 -> " +
                    "$endSeed3 -> $endSeed4 -> $endSeed5 -> $endSeed6. " +
                    "$inStartSourceText0 $inEndSourceText0 " +
                    "$inStartSourceText1 $inEndSourceText1 " +
                    "$inStartSourceText2 $inEndSourceText2 " +
                    "$inStartSourceText3 $inEndSourceText3 " +
                    "$inStartSourceText4 $inEndSourceText4 " +
                    "$inStartSourceText5 $inEndSourceText5 " +
                    "$inStartSourceText6 $inEndSourceText6 " +
                    ""
        )

        if (inStartSources0 || inEndSources0 ||
            inStartSources1 || inEndSources1 ||
            inStartSources2 || inEndSources2 ||
            inStartSources3 || inEndSources3 ||
            inStartSources4 || inEndSources4 ||
            inStartSources5 || inEndSources5 ||
            inStartSources6 || inEndSources6
        ) {
            seedCheckList.add(startSeed0)
        }
    }

    println("Minimal set of seeds that need to be checked: $seedCheckList")
}

fun thing(category: Category, seed: Long): Pair<Boolean, Long> {
    val firstNextSeed = category.list.find { it.contains(seed) }?.next(seed) ?: seed
    val firstKeyPoints = category.list.map { it.source to it.source + it.length }
    return firstKeyPoints.any { it.first == seed || it.second == seed } to firstNextSeed
}

/**
 * Experiment 5
 */

//
//
//fun thing(category: Category, seed: Long): Pair<Boolean, Long>  {
//    val firstNextSeed = category.list.find { it.contains(seed) }?.next(seed) ?: seed
//    val firstKeyPoints = category.list.map { it.source to it.source + it.length }
//    return firstKeyPoints.any { it.first == seed || it.second == seed } to firstNextSeed
//}
//
//fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/day05/input/input_part1_test.txt")
////    val input = File("src/jvmMain/kotlin/day05/input/input.txt")
//    val lines = input.readLines()
//
//    val seedNumbers = lines[0].split(":")[1].trim().split(" ").map { it.toLong() }
//    val seedList = seedNumbers.foldIndexed(emptyList<Seed>()) { index, acc, next ->
//        if (index % 2 == 0) {
//            acc.add(Seed(number = next, length = 0))
//        } else {
//            acc.updateLast { copy(length = next) }
//        }
//    }
//
//    val seeds = seedList.flatMap { List(it.length.toInt()) { index -> it.number + index } }
//
//    println("Seeds: $seeds")
//
//    val categoryList = lines.drop(2).fold(emptyList<Category>()) { acc, next ->
//        if (next.contains("map")) {
//            acc.add(Category(emptyList()))
//        } else if (next.isBlank()) {
//            // complete category
//            acc
//        } else {
//            val (destination, source, length) = next.split(" ").map { it.toLong() }
//            val map = Map(destination = destination, source = source, length = length)
//
//            val last = acc.last()
//
//            val category = Category(last.list.add(map))
//
//            acc.updateLast(category)
//        }
//    }.map { category -> category.copy(list = category.list.sortedBy { map -> map.source }) }
//
////    val keyPoints = categoryList.map { it.list.map { it.source } }
////    println("Key points: ${keyPoints.take(2)}")
//
//    println(categoryList.joinToString("\n"))
//
//    /***** Experiments *////
//
//
//    /**
//     * Experiment 4
//     */
//    val listOfSeedsToCheck = listOf(50L).filter { seed ->
////    val listOfSeedsToCheck = (79..92L).filter { seed ->
//        val category0 = categoryList[0]
//        val (contains1, seed1) = thing(category0, seed)
//
//        val category1 = categoryList[1]
//        val (contains2, seed2) = thing(category1, seed1)
//
//        val category2 = categoryList[2]
//        val (contains3, seed3) = thing(category2, seed2)
//
//        val category3 = categoryList[3]
//        val (contains4, seed4) = thing(category3, seed3)
//
//        val category4 = categoryList[4]
//        val (contains5, seed5) = thing(category4, seed4)
//
//        val category5 = categoryList[5]
//        val (contains6, seed6) = thing(category5, seed5)
//
//        val category6 = categoryList[6]
//        val (contains7, seed7) = thing(category6, seed6)
////        println(category6)
//
//        println("$seed -> $seed1 -> $seed2 -> $seed3 -> $seed4 -> $seed5 -> $seed6 -> $seed7")
//        println("$contains1 $contains2 $contains3 $contains4 $contains5 $contains6 $contains7")
//        println()
//        true
//    }
//
//    println(listOfSeedsToCheck)


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

//}

fun Boolean.display(): String {
    return if (this) "_TRUE" else "false"
}

// Is there a way to combine categories so that I that instead of seed-to-soil to soil-to-fertilizer
//      we could directly do seed-to-fertilizer??
fun keyNumbersToCheck() {
}

data class Category(
    val name: String,
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

//fun solvePart1() {
////    val input = File("src/jvmMain/kotlin/day05/input/input_part1_test.txt")
//    val input = File("src/jvmMain/kotlin/day05/input/input.txt")
//    val lines = input.readLines()
//
//    val seeds = lines[0].split(":")[1].trim().split(" ").map { it.toLong() }
//
//    val categoryList = lines.drop(2).fold(emptyList<Category>()) { acc, next ->
//        if (next.contains("map")) {
//            acc.add(Category(emptyList()))
//        } else if (next.isBlank()) {
//            // complete category
//            acc
//        } else {
//            val (destination, source, length) = next.split(" ").map { it.toLong() }
//            val map = Map(destination = destination, source = source, length = length)
//
//            val last = acc.last()
//
//            val category = Category(last.list.add(map))
//
//            acc.updateLast(category)
//        }
//    }
//
//    val min = seeds.minOfOrNull { seed ->
//        val res = categoryList.fold(seed) { acc, category ->
//            category.list.find { it.contains(acc) }?.next(acc) ?: acc
//        }
//        println(res)
//        println()
//        res
//    }
//
//    println("min: $min")
//}
