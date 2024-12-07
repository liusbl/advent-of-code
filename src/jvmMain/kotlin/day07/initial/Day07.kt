package day07.initial

import java.io.File
import kotlin.math.pow

fun main() {
    // Started: 2024-12-07 18:00
    // Finished: 2024-12-07 19:23
    // Solution: 850435817339
//    solvePart1()

    // Started:
    // Finished:
    // Solution:
    solvePart2()
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
    val input = File("src/jvmMain/kotlin/day07/input/input_part1_test.txt")
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
            if (i % modulo >= modulo / 2) {
                array[j++] = symbols[1]
            } else {
                array[j++] = symbols[0]
            }
            modulo /= 2
        }
        result.add(array)
    }
    return result.map { it.filterNotNull() }
}
