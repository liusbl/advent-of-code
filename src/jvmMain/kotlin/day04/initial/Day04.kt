package day04.initial

import java.io.File
import kotlin.math.abs

fun main() {
    test() // Needed to verify text directions

    // Started: 2024-12-04 11:02
    // Finished:  2024-12-04 11:44
    // Solution: 2468
//    solvePart1()

    // Started: 2024-12-04 11:44
    // Finished: 2024-12-04 11:55
    // Solution: 1864
//    solvePart2()
}


fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day04/input/input.txt")
    val lines = input.readLines()
    var count = 0
    lines.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { letterIndex, letter ->
            if (lineIndex == 0 || letterIndex == 0 || lineIndex == lines.size - 1 || letterIndex == line.length - 1) {
                // Nothing
            } else {
                if (letter == 'A') {
                    // Attempt to find MAS from 4 directions: UpLeft, UpRight, BottomRight, BottomLeft
                    val masCount = listOf(
                        lines.text(Down + Right, lineIndex - 1, letterIndex - 1, 3),
                        lines.text(Down + Left, lineIndex - 1, letterIndex + 1, 3),
                        lines.text(Up + Left, lineIndex + 1, letterIndex + 1, 3),
                        lines.text(Up + Right, lineIndex + 1, letterIndex - 1, 3)
                    ).count { it == "MAS" }
                    println(masCount)
                    if (masCount == 2) {
                        count++
                    }
                }
            }
        }
    }

    val result = count

    println(result)
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
                    lines.text(indexUpdater = Up, lineIndex, letterIndex, letterCount = 4),
                    lines.text(indexUpdater = Up + Right, lineIndex, letterIndex, letterCount = 4),
                    lines.text(indexUpdater = Right, lineIndex, letterIndex, letterCount = 4),
                    lines.text(indexUpdater = Down + Right, lineIndex, letterIndex, letterCount = 4),
                    lines.text(indexUpdater = Down, lineIndex, letterIndex, letterCount = 4),
                    lines.text(indexUpdater = Down + Left, lineIndex, letterIndex, letterCount = 4),
                    lines.text(indexUpdater = Left, lineIndex, letterIndex, letterCount = 4),
                    lines.text(indexUpdater = Up + Left,lineIndex, letterIndex, letterCount = 4)
                ).count { it == "XMAS" }
            }
        }
    }

    val result = count

    println(result)
}

private fun List<String>.text(indexUpdater: IndexUpdater, lineIndex: Int, letterIndex: Int, letterCount: Int): String {
    var count = 0
    var result = ""
    var point: Point? = Point(lineIndex, letterIndex)
    while (count++ < letterCount) {
        point!!
        result += this[point.lineIndex][point.letterIndex]
        point = indexUpdater.update(this, point)
        if (point == null) {
            break
        }
    }
    return result
}

data class Point(
    val lineIndex: Int,
    val letterIndex: Int
)

interface IndexUpdater {
    fun update(lines: List<String>, point: Point): Point?

    operator fun plus(updater: IndexUpdater): IndexUpdater {
        return object : IndexUpdater {
            override fun update(lines: List<String>, point: Point): Point? {
                val newPoint = this@IndexUpdater.update(lines, point) ?: return null
                return updater.update(lines, newPoint)
            }
        }
    }
}

object Up : IndexUpdater {
    override fun update(lines: List<String>, point: Point): Point? =
        point.copy(lineIndex = point.lineIndex - 1).takeIf { it.lineIndex >= 0 }
}

object Right : IndexUpdater {
    override fun update(lines: List<String>, point: Point): Point? =
        point.copy(letterIndex = point.letterIndex + 1).takeIf { it.letterIndex < lines[0].length }
}

object Down : IndexUpdater {
    override fun update(lines: List<String>, point: Point): Point? =
        point.copy(lineIndex = point.lineIndex + 1).takeIf { it.lineIndex < lines.size }
}

object Left : IndexUpdater {
    override fun update(lines: List<String>, point: Point): Point? =
        point.copy(letterIndex = point.letterIndex - 1).takeIf { it.letterIndex >= 0 }
}

val updaterList = listOf(
    Up,
    Up + Right,
    Right,
    Down + Right,
    Down,
    Down + Left,
    Left,
    Up + Left
)

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

    val result = input.lines().text(Up, 4, 1, 4)

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

    val result = input.lines().text(Up, 2, 1, 4)

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

    val result = input.lines().text(Up + Right,4, 1, 4)

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

    val result = input.lines().text(Up + Right, 4, 1, 4)

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

    val result = input.lines().text(Right, 4, 1, 4)

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

    val result = input.lines().text(Right, 4, 1, 4)

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

    val result = input.lines().text(Down + Right, 2, 1, 4)

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

    val result = input.lines().text(Down + Right, 2, 1, 4)

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

    val result = input.lines().text(Down, 2, 1, 4)

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

    val result = input.lines().text(Down, 2, 1, 4)

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

    val result = input.lines().text(Down + Left, 2, 4, 4)

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

    val result = input.lines().text(Down + Left, 2, 2, 4)

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

    val result = input.lines().text(Left, 2, 4, 4)

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

    val result = input.lines().text(Left, 2, 2, 4)

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

    val result = input.lines().text(Up + Left, 4, 4, 4)

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

    val result = input.lines().text(Up + Left, 4, 2, 4)

    val expected = "XMA"
    if (expected == result) {
        println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    } else {
        System.err.println("${object {}.javaClass.enclosingMethod.name}. Expected: $expected, result: $result. Matches: ${expected == result}")
    }
}

