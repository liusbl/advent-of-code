package _2023.day04.initial

import java.io.File

fun main() {
//    solvePart1() // Solution: 21138, time: 07:09
    solvePart2() // Solution: 7185540, time: 08:21
}

fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/day04/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day04/input/input.txt")
    val lines = input.readLines()

    val matchList = lines.map { line ->
        val (winningLine, yourLine) = line.split(":")[1].split("|").map { it.trim() }
        val winning = winningLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val your = yourLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val number = line.split(":")[0].replace("Card", "").trim().toInt()
        Match(
            number = number,
            copies = 1,
            winning = winning,
            your = your
        )
    }.toMutableList()

    var index = 0
    println("Round $index, \n${matchList.joinToString("\n")}\n")

    while (index <= matchList.size - 1) {
        val match = matchList[index]
        val additions = matchList.subList(index + 1, index + 1 + match.yourWinning.size)
            .map { it.copy(copies = match.copies) }
        ((index + 1)..(index + match.yourWinning.size)).forEachIndexed { loopIndex, additionIndex ->
            val addition = additions[loopIndex]
            matchList[additionIndex] = addition.copy(copies = addition.copies + matchList[additionIndex].copies)
        }
        println("Round $index, \n${matchList.joinToString("\n")}\n")
        index++
    }

    println(matchList.sumOf { it.copies })
}

data class Match(
    val number: Int,
    val copies: Int,
    val winning: List<Int>,
    val your: List<Int>
) {
    val yourWinning = your.filter { winning.contains(it) }
    override fun toString(): String {
        return "Match(number=$number, copies=$copies, winning=$winning, your=$your, yourWinning=$yourWinning)"
    }

}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day04/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day04/input/input_part1_test.txt")
    val lines = input.readLines()

    val res = lines.map { line ->
        val (winningLine, yourLine) = line.split(":")[1].split("|").map { it.trim() }
        val winning = winningLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val your = yourLine.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        your.filter { winning.contains(it) }
    }.filter { it.isNotEmpty() }.sumOf { yourWinningNumbers ->
        yourWinningNumbers.fold(1) { acc, next ->
            acc * 2
        } / 2
    }

    println(res)

    val result = "result"
    println(result)
}
