package _2024.day09.initial

import util.setRange
import java.io.File

fun main() {
    // Started: 2024-12-12 14:20
    // Finished: 2024-12-12 15:05
    // Solution: 6262891638328
    // 829320760 is too low. Was using Int instead of Long
    // 6262891638328
//    solvePart1()

    // Started: 2024-12-12 15:11
    // Finished: 2024-12-12 15:40
    // Solution: 6287317016845
    solvePart2()
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day09/input/input.txt")
    val line = input.readLines()[0].map { it.digitToInt() }

    val blocks = line.foldIndexed(listOf<Block>()) { index, acc, digit ->
        acc + if (index % 2 == 0) {
            List(digit) { Block.File(index / 2.toLong()) }
        } else {
            List(digit) { Block.Empty }
        }
    }

    println(blocks.toDisplayString())

    val optimizedBlocks = blocks.toMutableList()

    val distinctFiles = blocks.filterIsInstance<Block.File>().distinct()
    distinctFiles.reversed().forEach { file ->
        val firstIndex = optimizedBlocks.indexOfFirst { it == file }
        val lastIndex = optimizedBlocks.indexOfLast { it == file }
        val section = optimizedBlocks.subList(firstIndex, lastIndex + 1)
       // println("Trying section: ${section.toDisplayString()}")
        val emptySectionIndex = optimizedBlocks.withIndex().indexOfFirst { (index, _) ->
            if (index >= firstIndex) {
                return@indexOfFirst false
            }
           // println(optimizedBlocks.subList(index, index + section.size).toDisplayString())
            optimizedBlocks.subList(index, index + section.size).all { it == Block.Empty }
        }
        if (emptySectionIndex > -1) {
            optimizedBlocks.setRange(emptySectionIndex, section)
            optimizedBlocks.setRange(firstIndex, List(section.size) { Block.Empty })
          //  println(optimizedBlocks.toDisplayString())
        }
    }

    println(optimizedBlocks.toDisplayString())

    val checksum = optimizedBlocks.withIndex().sumOf { (index, block) ->
        when (block) {
            Block.Empty -> 0
            is Block.File -> index * block.id
        }
    }

    println(checksum)
}

sealed interface Block {
    data class File(val id: Long) : Block

    object Empty : Block
}

fun List<Block>.toDisplayString(): String {
    return joinToString(separator = "", transform = { block ->
        when (block) {
            is Block.Empty -> "."
            is Block.File -> "${block.id}"
        }
    })
}

fun solvePart1() {
    val input = File("src/jvmMain/kotlin/day09/input/input.txt")
    val line = input.readLines()[0].map { it.digitToInt() }

    val blocks = line.foldIndexed(listOf<Block>()) { index, acc, digit ->
        acc + if (index % 2 == 0) {
            List(digit) { Block.File(index / 2.toLong()) }
        } else {
            List(digit) { Block.Empty }
        }
    }

    println(blocks.toDisplayString())

    val optimizedBlocks = blocks.toMutableList()
    for ((index, block) in blocks.reversed().withIndex()) {
        try {
            if (block is Block.File) {
                val newIndex = optimizedBlocks.indexOfFirst { it is Block.Empty }
                if (newIndex > optimizedBlocks.indexOfLast { it is Block.File }) {
                    println("Finished optimizing #1")
                    break
                }
                optimizedBlocks[newIndex] = block
                optimizedBlocks[blocks.size - index - 1] = Block.Empty
                //  println(optimizedBlocks.toDisplayString())
            }
        } catch (_: Exception) {
            println("Finished optimizing #2")
            break
        }
    }

    println(optimizedBlocks.toDisplayString())

    val checksum = optimizedBlocks.withIndex().sumOf { (index, block) ->
        when (block) {
            Block.Empty -> 0
            is Block.File -> index * block.id
        }
    }

    println(checksum)
}