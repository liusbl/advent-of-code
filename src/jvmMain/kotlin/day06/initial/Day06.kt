package day06.initial

import java.io.File

/**

Time:      7  15   30
Record:  9  40  200

 0 -> 0
 1 -> (7-1)*1=6
 2 -> (7-2)*2=10

 formula:
h = hold
f = result time
t = time
d = distance

 f = (t - h) * h

 /. Maybe use something like binary search to find the nearest record?
 // Otherwise maybe something with calculus?
 */
fun main() {
    solvePart1() // Solution:
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day06/input/input.txt")
    val (timeText, recordText) = input.readLines()
    val times = timeText.split(" ").filter { it.isNotBlank() }.map { it.toLong() }
    val records = recordText.split(" ").filter { it.isNotBlank() }.map { it.toLong() }

    val raceList = times.zip(records) { time, record -> Race(time, record) }

    val result = "result"

    println(raceList)
}

data class Race(
    val time: Long,
    val record: Long
)