package _2023.day08.initial

import java.io.File

fun main() {
//    solvePart1() ///// Solution: 19637, Time: 20:30
    solvePart2() ///// Solution: 19637, Time: 20:30
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

fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/day08/input/input_part2_test.txt")
    val input = File("src/jvmMain/kotlin/day08/input/input.txt")
    val lines = input.readLines()

    val directionList = lines[0].trim().map(::Direction)
    val nodeList = lines.subList(2, lines.size).map(::Node)

    val initialNodeList = nodeList.filter { it.name.endsWith("A") }
    var interimResult = InterimResult(
        currentNodeList = initialNodeList,
        directionIndex = 0,
        count = 0
    )

    println("Start nodes: ${initialNodeList}")

    while (!interimResult.currentNodeList.all { it.name.endsWith("Z") }) {
        val newNodeList = interimResult.currentNodeList.map { currentNode ->
            operate(
                node = currentNode,
                directionIndex = interimResult.directionIndex,
                count = interimResult.count,
                directionList = directionList,
                nodeList = nodeList
            )
        }

        val newDirectionIndex = if (interimResult.directionIndex + 1 >= directionList.size) {
            0
        } else {
            interimResult.directionIndex + 1
        }

        val newCount = interimResult.count + 1

        interimResult = InterimResult(
            currentNodeList = newNodeList,
            directionIndex = newDirectionIndex,
            count = newCount
        )
        println(interimResult)
    }

    println(interimResult.count)
}

data class InterimResult(
    val currentNodeList: List<Node>,
    val directionIndex: Int,
    val count: Int
)

private fun operate(
    node: Node,
    directionIndex: Int,
    count: Int,
    directionList: List<Direction>,
    nodeList: List<Node>
): Node {
    var newNode = node
//    while (!newNode.final) {
        val direction = directionList[directionIndex]
        newNode = when (direction) {
            Direction.Left -> nodeList.first { it.name == newNode.leftName }
            Direction.Right -> nodeList.first { it.name == newNode.rightName }
        }

        val newDirection = directionList[directionIndex]
//        println("Node: $newNode, step: $count, directionIndex: ${directionIndex}, newDirection: $newDirection")
//    }
    return newNode
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
    val final: Boolean = name == "ZZZ"
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