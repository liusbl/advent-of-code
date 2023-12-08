package day08.initial

import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day08/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day08/input/input.txt")
    val lines = input.readLines()

    val directionList = lines[0].trim().map(::Direction)
    val nodeList = lines.subList(2, lines.size).map(::Node)

    var currentNode = nodeList[0]
    var directionIndex = 0
    var count = 0
    while (!currentNode.final) {
        val direction = directionList[directionIndex]
        currentNode = when (direction) {
            Direction.Left -> nodeList.first { it.name == currentNode.leftName }
            Direction.Right -> nodeList.first { it.name == currentNode.rightName }
        }
        if (directionIndex + 1 >= directionList.size) {
            directionIndex = 0
        } else {
            directionIndex++
        }
        count++
        val newDirection = directionList[directionIndex]
        println("Node: $currentNode, step: $count, directionIndex: ${directionIndex}, newDirection: $newDirection")
    }

    println(count)
}

fun Node(line: String): Node {
    val (name, directions) = line.split("=").map { it.trim() }
    val (left, right) = directions.split(", ").map { it.replace("(", "").replace(")", "").trim() }
    return Node(
        name = name,
        leftName = left,
        rightName = right
    )
}

data class Node(
    val name: String,
    val leftName: String,
    val rightName: String
) {
//    val final: Boolean = name == "ZZZ"
    val final: Boolean = name == "SKN"
}

fun Direction(char: Char): Direction = when (char) {
    'L' -> Direction.Left
    'R' -> Direction.Right
    else -> error("Invalid direction character: $char")
}

enum class Direction {
    Left,
    Right;
}