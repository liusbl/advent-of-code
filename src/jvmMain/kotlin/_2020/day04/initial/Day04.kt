package _2020.day04.initial

import java.io.File

fun main() {
    // Started:
    // Finished:
    // Solution:
    solvePart1()

    // Started:dfsadas
    // Finished:
    // Solution:
//    solvePart2()
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/_2020/day04/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/_2020/day04/input/input.txt")
    val text = input.readText()

    val mandatory = """
        byr
        iyr
        eyr
        hgt
        hcl
        ecl
        pid
    """.trimIndent().lines()

    val optional = listOf("cid")

    val passportTexts = text.split("\n\n")
    val passports = passportTexts.map { it.replace("\n", " ") }.map { text ->
        text.split(" ")
    }

    val result = passports.count { passport ->
        val fields =passport.map { it.split(":")[0] }
        mandatory.all { fields.contains(it) }
    }

    println(result)
}

fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/_2020/day04/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/_2020/day04/input/input.txt")
    val lines = input.readLines()

    val result = lines.count { line ->
        val (positionListString, letter, password) = line.split(" ").filter { it.isNotBlank() }

        val (start, end) = positionListString.split("-").map(String::toInt)

        (password[start - 1] == letter[0]) xor (password[end - 1] == letter[0])
    }

    println(result)
}