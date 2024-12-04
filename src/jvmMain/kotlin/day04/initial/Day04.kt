package day04.initial

import java.io.File
import kotlin.math.abs

fun main() {
//    test() // Needed to verify text directions

    // Started: 2024-12-04 11:02
    // Finished:  2024-12-04 11:44
    // Solution: 2468
    solvePart1()

    // Started:
    // Finished:
    // Solution:
    //solvePart2()
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day04/input/input.txt")
    val lines = input.readLines()
    var count = 0
    lines.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { letterIndex, letter ->
            if (letter == 'X') {
                // Attempt to find XMAS in all directions
                count += listOf(
                    lines.textUp(lineIndex, letterIndex),
                    lines.textUpRight(lineIndex, letterIndex),
                    lines.textRight(lineIndex, letterIndex),
                    lines.textDownRight(lineIndex, letterIndex),
                    lines.textDown(lineIndex, letterIndex),
                    lines.textDownLeft(lineIndex, letterIndex),
                    lines.textLeft(lineIndex, letterIndex),
                    lines.textUpLeft(lineIndex, letterIndex)
                ).count { it == "XMAS" }
            }
        }
    }

    val result = count

    println(result)
}


private fun List<String>.textUp(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    var lineIndexAddition = 0
    var result = ""
    while (abs(lineIndexAddition) < letterCount) {
        if (lineIndex + lineIndexAddition < 0) break
        result += this[lineIndex + lineIndexAddition--][letterIndex]
    }
    return result
}

private fun List<String>.textUpRight(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    val width = this[0].length
    var lineIndexAddition = 0
    var letterIndexAddition = 0
    var result = ""
    while (abs(lineIndexAddition) < letterCount) {
        if (lineIndex + lineIndexAddition < 0) break
        if (letterIndex + letterIndexAddition >= width) break
        result += this[lineIndex + lineIndexAddition--][letterIndex + letterIndexAddition++]
    }
    return result
}

private fun List<String>.textRight(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    val width = this[0].length
    var letterIndexAddition = 0
    var result = ""
    while (abs(letterIndexAddition) < letterCount) {
        if (letterIndex + letterIndexAddition >= width) break
        result += this[lineIndex][letterIndex + letterIndexAddition++]
    }
    return result
}


private fun List<String>.textDownRight(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    val width = this[0].length
    val height = this.size
    var lineIndexAddition = 0
    var letterIndexAddition = 0
    var result = ""
    while (abs(lineIndexAddition) < letterCount) {
        if (lineIndex + lineIndexAddition >= height) break
        if (letterIndex + letterIndexAddition >= width) break
        result += this[lineIndex + lineIndexAddition++][letterIndex + letterIndexAddition++]
    }
    return result
}

private fun List<String>.textDown(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    val height = this.size
    var lineIndexAddition = 0
    var result = ""
    while (abs(lineIndexAddition) < letterCount) {
        if (lineIndex + lineIndexAddition >= height) break
        result += this[lineIndex + lineIndexAddition++][letterIndex]
    }
    return result
}

private fun List<String>.textDownLeft(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    val height = this.size
    var lineIndexAddition = 0
    var letterIndexAddition = 0
    var result = ""
    while (abs(lineIndexAddition) < letterCount) {
        if (lineIndex + lineIndexAddition >= height) break
        if (letterIndex + letterIndexAddition < 0) break
        result += this[lineIndex + lineIndexAddition++][letterIndex + letterIndexAddition--]
    }
    return result
}

private fun List<String>.textLeft(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    var letterIndexAddition = 0
    var result = ""
    while (abs(letterIndexAddition) < letterCount) {
        if (letterIndex + letterIndexAddition < 0) break
        result += this[lineIndex][letterIndex + letterIndexAddition--]
    }
    return result
}

private fun List<String>.textUpLeft(lineIndex: Int, letterIndex: Int, letterCount: Int = 4): String {
    var lineIndexAddition = 0
    var letterIndexAddition = 0
    var result = ""
    while (abs(lineIndexAddition) < letterCount) {
        if (lineIndex + lineIndexAddition < 0) break
        if (letterIndex + letterIndexAddition < 0) break
        result += this[lineIndex + lineIndexAddition--][letterIndex + letterIndexAddition--]
    }
    return result
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day04/input/input_part1_test.txt")
    val lines = input.readLines()

    val result = "result"

    println(result)
}

private fun test() {
    testTextUp_regular()
    testTextUp_cut_off()
    testTextUpRight_regular()
    testTextUpRight_cut_off()
    testTextRight_regular()
    testTextRight_cut_off()
    testTextDownRight_regular()
    testTextDownRight_cut_off()
    testTextDown_regular()
    testTextDown_cut_off()
    testTextDownLeft_regular()
    testTextDownLeft_cut_off()
    testTextLeft_regular()
    testTextLeft_cut_off()
    testTextUpLeft_regular()
    testTextUpLeft_cut_off()
}

private fun testTextUp_regular() {
    val input = """
        ****
        *S**
        *A**
        *M**
        *X**
        ****
    """.trimIndent()

    val result = input.lines().textUp(4, 1)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextUp_cut_off() {
    val input = """
        *A**
        *M**
        *X**
        ****
    """.trimIndent()

    val result = input.lines().textUp(2, 1)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextUpRight_regular() {
    val input = """
        ******
        ****S*
        ***A**
        **M***
        *X****
        ******
    """.trimIndent()

    val result = input.lines().textUpRight(4, 1)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextUpRight_cut_off() {
    val input = """
        ****
        ****
        ***A
        **M*
        *X**
        ****
    """.trimIndent()

    val result = input.lines().textUpRight(4, 1)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextRight_regular() {
    val input = """
        ******
        ******
        ******
        ******
        *XMAS*
        ******
    """.trimIndent()

    val result = input.lines().textRight(4, 1)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextRight_cut_off() {
    val input = """
        ****
        ****
        ****
        ****
        *XMA
        ****
    """.trimIndent()

    val result = input.lines().textRight(4, 1)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextDownRight_regular() {
    val input = """
        ******
        ******
        *X****
        **M***
        ***A**
        ****S*
        ******
    """.trimIndent()

    val result = input.lines().textDownRight(2, 1)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextDownRight_cut_off() {
    val input = """
        ******
        ******
        *X****
        **M***
        ***A**
    """.trimIndent()

    val result = input.lines().textDownRight(2, 1)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextDown_regular() {
    val input = """
        ******
        ******
        *X****
        *M****
        *A****
        *S****
        ******
    """.trimIndent()

    val result = input.lines().textDown(2, 1)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextDown_cut_off() {
    val input = """
        ******
        ******
        *X****
        *M****
        *A****
    """.trimIndent()

    val result = input.lines().textDown(2, 1)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextDownLeft_regular() {
    val input = """
        ******
        ******
        ****X*
        ***M**
        **A***
        *S****
        ******
    """.trimIndent()

    val result = input.lines().textDownLeft(2, 4)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextDownLeft_cut_off() {
    val input = """
        ****
        ****
        **X*
        *M**
        A***
        ****
        ****
    """.trimIndent()

    val result = input.lines().textDownLeft(2, 2)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextLeft_regular() {
    val input = """
        ******
        ******
        *SAMX*
        ******
        ******
        ******
        ******
    """.trimIndent()

    val result = input.lines().textLeft(2, 4)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextLeft_cut_off() {
    val input = """
         ****
         ****
         AMX*
         ****
         ****
         ****
         ****
    """.trimIndent()

    val result = input.lines().textLeft(2, 2)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}


private fun testTextUpLeft_regular() {
    val input = """
        ******
        *S****
        **A***
        ***M**
        ****X*
        ******
        ******
    """.trimIndent()

    val result = input.lines().textUpLeft(4, 4)

    val expected = "XMAS"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

private fun testTextUpLeft_cut_off() {
    val input = """
         ****
         ****
         A***
         *M**
         **X*
         ****
         ****
    """.trimIndent()

    val result = input.lines().textUpLeft(4, 2)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

