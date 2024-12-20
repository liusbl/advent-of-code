package day18.initial

import java.io.File

fun main() {
    // Started: 2024-12-18 14:15
    // Finished:
    // Solution:
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day18/input/input_part1_test.txt")

    val lines = """
        ...#...
        ..#..#.
        ....#..
        ...#..#
        ..#..#.
        .#..#..
        #.#....
    """.trimIndent()

    val g = lines.lines().map { it.toCharArray() }.toTypedArray()

    val adj = mutableMapOf<Pair<Int, Int>, List<Pair<Int, Int>>>()

    for ((i, line) in g.withIndex()) {
        for ((j, c) in line.withIndex()) {
            if (c == '#') continue
            val up = runCatching {
                val a = g[i - 1][j]
                if (a != '#') {
                    i - 1 to j
                } else {
                    error("")
                }
            }.getOrNull()
            val right = runCatching {
                val a = g[i][j + 1]
                if (a != '#') {
                    i to j + 1
                } else {
                    error("")
                }
            }.getOrNull()
            val down = runCatching {
                val a = g[i + 1][j]
                if (a != '#') {
                    i + 1 to j
                } else {
                    error("")
                }
            }.getOrNull()
            val left = runCatching {
                val a = g[i][j - 1]
                if (a != '#') {
                    i to j - 1
                } else {
                    error("")
                }
            }.getOrNull()
//            println("$i, $j, $up, $right, $down, $left")
            adj[i to j] = listOfNotNull(up, right, down, left)
        }
    }

    println(adj.toList().joinToString(separator = "\n"))
    println()

    shortestPath(adj, 0 to 0, 6 to 6)
}

private fun <T> shortestPath(adj: Map<T, List<T>>, s: T, d: T): Int {
    val visitedCount = mutableSetOf<Pair<T, Int>>()
    val current = mutableListOf<T>()

    current.add(s)

//    while (current.isNotEmpty()) {
//        val node = current.removeFirstOrNull() ?: break
//        val adjacentNodes = adj[node]!!.toList()
//        if (visitedCount.contains(node)) continue
//        visitedCount.add(node)
//        println("visiting: $node. adjacent: $adjacentNodes")
//        current.addAll(adjacentNodes)
//    }

    return 0
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day18/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}