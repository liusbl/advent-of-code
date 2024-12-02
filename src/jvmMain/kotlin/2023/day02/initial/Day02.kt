package day02.initial

import java.io.File

fun main() {
//    solvePart1() // Solution: 2268, solved at 07:31
    solvePart2() // Solution: 63542, solved at 07:46
}

// max amount of: 12 red cubes, 13 green cubes, and 14 blue cubes
fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day02/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day02/input/input_part1_test.txt")
    val lines = input.readLines()

    val gameList = lines.map(::Game)

    val result = gameList.filter { game ->
        val cubeDrawList = game.list
        cubeDrawList.all {
            val redCubeCount = it.list.firstOrNull { it.color == Color.Red }?.count ?: 0
            val greenCubeCount = it.list.firstOrNull { it.color == Color.Green }?.count ?: 0
            val blueCubeCount = it.list.firstOrNull { it.color == Color.Blue }?.count ?: 0
            redCubeCount <= 12 && greenCubeCount <= 13 && blueCubeCount <= 14
        }
    }.sumOf { it.id }

    println(result)
}

fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/day02/input/input.txt")
    val input = File("src/jvmMain/kotlin/day02/input/input.txt")
    val lines = input.readLines()

    val gameList = lines.map(::Game)

    val initialHighestList = listOf(
        Cube(count = 0, color = Color.Red),
        Cube(count = 0, color = Color.Green),
        Cube(count = 0, color = Color.Blue)
    )
    val result = gameList.map { game ->
        game.list.fold(GameWithCount(initialHighestList, game)) { acc, cubeDraw ->
            val redCubeCount = cubeDraw.list.firstOrNull { it.color == Color.Red }?.count ?: 0
            val greenCubeCount = cubeDraw.list.firstOrNull { it.color == Color.Green }?.count ?: 0
            val blueCubeCount = cubeDraw.list.firstOrNull { it.color == Color.Blue }?.count ?: 0

            val redMaxCount = maxOf(acc.highestCubeCount[0].count, redCubeCount)
            val greenMaxCount = maxOf(acc.highestCubeCount[1].count, greenCubeCount)
            val blueMaxCount = maxOf(acc.highestCubeCount[2].count, blueCubeCount)
            acc.copy(
                highestCubeCount = listOf(
                    Cube(count = redMaxCount, color = Color.Red),
                    Cube(count = greenMaxCount, color = Color.Green),
                    Cube(count = blueMaxCount, color = Color.Blue)
                )
            )
        }
    }.sumOf {
        it.highestCubeCount[0].count * it.highestCubeCount[1].count * it.highestCubeCount[2].count
    }

    println(result)
}

data class GameWithCount(
    val highestCubeCount: List<Cube>, // red 23, green 54, blue 123
    val game: Game
)

// Parsing
fun Game(line: String): Game {
    val split = line.split(":")
    val cubeDraw = split[1].trim()
        .split(";")
        .map(::CubeDraw)
    val id = split[0].split(" ")[1].toInt()
    return Game(id = id, list = cubeDraw)
}

// 3 blue, 4 red
fun CubeDraw(cubeText: String): CubeDraw {
    val list = cubeText.trim()
        .split(",")
        .map(::Cube)
    return CubeDraw(list)
}

// 4 red
fun Cube(cubeText: String): Cube {
    val count = cubeText.trim().split(" ")[0].toInt()
    val color = Color.values().first { cubeText.contains(it.text) }
    return Cube(
        count = count,
        color = color
    )
}

data class Game(
    val id: Int,
    val list: List<CubeDraw>
)

data class CubeDraw(
    val list: List<Cube>
)

data class Cube(
    val count: Int,
    val color: Color
)

enum class Color(val text: String) {
    Blue("blue"),
    Red("red"),
    Green("green")
}