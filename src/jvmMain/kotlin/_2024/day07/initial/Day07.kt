package day07.initial

import java.io.File
import kotlin.math.pow

fun main() {
    // Started: 2024-12-07 18:00
    // Finished: 2024-12-07 19:23
    // Solution: 850435817339
//    solvePart1()

    // Started:
    // Finished:  2024-12-07 20:41
    // Solution: 104824810233437

    println(permutate(listOf("a", "b"), 3).joinToString("\n"))
    println(permutate(listOf("a", "b", "c"), 3).joinToString("\n"))

    solvePart2()

//    println(rangeIndex(1, 2, 0, 21))
}


fun rangeIndex(number: Int, divisions: Int, from: Int, to: Int): Int {
    val difference = to - from // 6 - 2 = 4
    val split = difference / divisions
    for (i in 0..divisions) {
        println(from + split * i)
//        if (number in ((from * i)..(from * (i + 1)))) {
//            return i
//        }
    }
    error("not found")
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day07/input/input.txt")
    val lines = input.readLines()

    val sum = lines.sumOf { line ->
        val (left, right) = line.split(":")
        val expected = left.toLong()
        val integers = right.drop(1).split(" ").map { it.toLong() }
        val operationCollection = permutate(listOf("+", "*"), integers.size - 1)
        val result = operationCollection.firstNotNullOfOrNull { operationList ->
            val res = integers.reduceIndexed { index, acc, integer ->
                if (index > operationList.size) return@reduceIndexed acc
                val operation = operationList[index - 1]
                when (operation) {
                    "+" -> {
                        acc + integer
                    }

                    "*" -> {
                        acc * integer
                    }

                    else -> error("dude")
                }
            }
            if (res == expected) expected else null
        }
        result ?: 0
    }

    val result = "result"

    println(sum)
}


fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day07/input/input.txt")
    val lines = input.readLines()

    val sum = lines.sumOf { line ->
        val (left, right) = line.split(":")
        val expected = left.toLong()
        val integers = right.drop(1).split(" ").map { it.toLong() }
        val operationCollection = permutate(listOf("+", "*", "|"), integers.size - 1)
        val result = operationCollection.firstNotNullOfOrNull { operationList ->
            val res = integers.reduceIndexed { index, acc, integer ->
                if (index > operationList.size) return@reduceIndexed acc
                val operation = operationList[index - 1]
                when (operation) {
                    "+" -> {
                        acc + integer
                    }

                    "*" -> {
                        acc * integer
                    }

                    "|" -> {
                        "$acc$integer".toLong()
                    }

                    else -> error("dude")
                }
            }
            if (res == expected) expected else null
        }
        result ?: 0
    }

    val result = "result"

    println(sum)
}

/**

+ + +
+ + *
+ * +
+ * *
 * + +
 * + *
 * * +
 * * *

/////////////
+ + +
+ + *
+ + |
+ * +
+ * *
+ * |
+ | +
+ | *
+ | |

 * + +
 * + *
 * + |
 * * +
 * * *
 * * |
 * | +
 * | *
 * | |

| + +
| + *
| + |
| * +
| * *
| * |
| | +
| | *
| | |

////////

+ +
+ *
+ |

 * +
 * *
 * |

| +
| *
| |
Lmao only works for two symbols..
 */
private fun permutate(symbols: List<String>, count: Int): List<List<String>> {
    val arrayCount = symbols.size.toDouble().pow(count.toDouble()).toLong()

    val result = mutableListOf<Array<String?>>()
    for (i in 0 until arrayCount) {
        var modulo = arrayCount
        val array = arrayOfNulls<String>(count)
        var j = 0
        while (modulo > 1) {
            if (symbols.size == 2) {
                if (i % modulo >= modulo / symbols.size) {
//                    println(
//                        "modulo: $modulo, i % modulo: ${i % modulo}, modulo / symbols.size: ${modulo / symbols.size}, " +
//                                "something: ${(i % modulo).mod(modulo / symbols.size)}, index: 1"
//                    )
                    array[j++] = symbols[1]
                } else {
//                    println(
//                        "modulo: $modulo, i % modulo: ${i % modulo}, modulo / symbols.size: ${modulo / symbols.size}, " +
//                                "something: ${(i % modulo).mod(modulo / symbols.size)}, index: 0"
//                    )
                    array[j++] = symbols[0]
                }
                modulo /= symbols.size
            } else if (symbols.size == 3) {
                if (i % modulo >= modulo / symbols.size * 2) {
//                    println(
//                        "modulo: $modulo, i % modulo: ${i % modulo}, modulo / symbols.size: ${modulo / symbols.size}, " +
//                                "something: ${(i % modulo).mod(modulo / symbols.size)}, index: 2"
//                    )
                    array[j++] = symbols[2]
                } else if (i % modulo >= modulo / symbols.size) {
//                    println(
//                        "modulo: $modulo, i % modulo: ${i % modulo}, modulo / symbols.size: ${modulo / symbols.size}, " +
//                                "something: ${(i % modulo).mod(modulo / symbols.size)}, index: 1"
//                    )
                    array[j++] = symbols[1]
                } else {
//                    println(
//                        "modulo: $modulo, i % modulo: ${i % modulo}, modulo / symbols.size: ${modulo / symbols.size}, " +
//                                "something: ${(i % modulo).mod(modulo / symbols.size)}, index: 0"
//                    )
                    array[j++] = symbols[0]
                }
                modulo /= symbols.size
            }
        }
        result.add(array)
    }
    return result.map { it.filterNotNull() }
}