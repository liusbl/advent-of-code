package day06.initial

import java.io.File
import kotlin.math.*

/**

Time:      7  15   30
Record:  9  40  200

0 -> 0
1 -> (7-1)*1=6
2 -> (7-2)*2=10
3 -> (7-3)*3=12
4 -> (7-4)*4=12
5 -> (7-5)*5=10
6 -> (7-6)*6=6
7 -> 0

formula:
h = hold
f = result time
t = time
d = distance

f = (t - h) * h
f = h*t - h*h
-h^2 + ht - f = 0
h^2 - ht + f = 0
h - ?

x^2 - xt + f = 0

(-b +- sqrt(b^2 - 4ac)) / 2

h = (t + sqrt(t^2 - 4f))/2
h = (t - sqrt(t^2 - 4f))/2

/. Maybe use something like binary search to find the nearest record?
// Otherwise maybe something with calculus?
 */
fun main() {
    solvePart1() // Solution: 131376, finished 14:08
//    solvePart2() // Solution: 34123437, finished 14:10
}

fun solve(time: Long, record: Long): Long {
    val t = time
    val f = record
    val h1 = (t - sqrt(t.toDouble().pow(2) - 4 * f)) / 2
    val h2 = (t + sqrt(t.toDouble().pow(2) - 4 * f)) / 2

    val dubiousLength = h2 - h1

    val startThing = if (h1.toLong().toDouble() == h1) {
        h1.toLong() + 1
    } else {
        ceil(h1).toLong()
    }

    val endThing = if (h2.toLong().toDouble() == h2) {
        h2.toLong() - 1
    } else {
        floor(h2).toLong()
    }


//    (0..t).forEach {
//        println("$it -> (${t}-$it)*$it=${(t - it) * it}")
//    }


    val result = endThing - startThing + 1
    println("time: $time, record: $record, h1: $h1, h2: $h2, length: $dubiousLength, startThing: $startThing, endThing: $endThing")
    println("result: $result")
    println()


    return result
}

// 143451 IS NOT RIGHT ANSWER
// 131376 is correct
fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day06/input/input_part1_test.txt")
//    val input = File("src/jvmMain/kotlin/day06/input/input.txt")
    val input = File("src/jvmMain/kotlin/day06/input/input_part2.txt")
    val (timeText, recordText) = input.readLines()
    val times = timeText.split(" ").filter { it.isNotBlank() }.map { it.toLong() }
    val records = recordText.split(" ").filter { it.isNotBlank() }.map { it.toLong() }

    val raceList = times.zip(records) { time, record -> Race(time, record) }


    val res = raceList.fold(1L) { acc, race ->
        acc * solve(race.time, race.record)
    }

    println(res)

//    val t = 7
//    val f = 9
//    val h1 = (t - sqrt(t.toDouble().pow(2) - 4 * f)) / 2
//    val h2 = (t + sqrt(t.toDouble().pow(2) - 4 * f)) / 2
//
//    println("$h1, $h2")
//
//    (0..7).forEach {
//        println("$it -> (${7}-$it)*$it=${(7 - it) * it}")
//    }
//    println()
//    println()
//
//
//    (0..15).forEach {
//        println("$it -> (${15}-$it)*$it=${(15 - it) * it}")
//    }
//    println()
//
//    (0..20).forEach {
//        println("$it -> (${20}-$it)*$it=${(20 - it) * it}")
//    }


    val result = "result"

    println(raceList)
}

data class Race(
    val time: Long,
    val record: Long
)