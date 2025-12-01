package _2024.day01.initial

import java.io.File
import kotlin.math.*

fun main() {
    solvePart1() // Solution: 1341714. Solved at: 2024-12-01 12:26
    solvePart2() // Solution: 27384707. Solved at: 2024-12-01 12:45
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day01/input/input.txt")
    val lines = input.readLines()
    val pairs = lines.map { it.split(" ").filter { !it.isBlank() }  }
        .filter { !it.isEmpty() }
        .map { it[0] to it[1] }

    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    pairs.forEach { pair ->
        list1.add(pair.first.toInt())
        list2.add(pair.second.toInt())
    }

    list1.sort()
    list2.sort()

    println(list1)
    println(list2)

    val sum = list1.sumOf { a -> a * list2.count { it == a } }

    println(sum)
}


fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day01/input/input.txt")
    val lines = input.readLines()

    val pairs = lines.map { it.split(" ").filter { it.isNotBlank() }  }
        .filter { it.isNotEmpty() }
        .map { it[0] to it[1] }

    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    pairs.forEach { pair ->
        list1.add(pair.first.toInt())
        list2.add(pair.second.toInt())
    }


    list1.sort()
    list2.sort()

    println(list1)
    println(list2)

    val sums = list1.zip(list2) { a, b ->
        abs(b - a)
    }

    println(sums)

    println(sums.sum())
}
