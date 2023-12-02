package day02.initial

import java.io.File

fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day02/input/input_part1_test.txt")
    val lines = input.readLines()

    val game = lines.map(::Game)

    println(game)
}

// Solving

// Parsing
fun Game(line: String): Game {
    val split = line.split(":")
    val cubeDraw = split[1].trim()
        .split(";")
        .map(::CubeDraw)
    val id = split[0].split(" ")[1].toInt()
    return Game(id = id,list = cubeDraw)
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