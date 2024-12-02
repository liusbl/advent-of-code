package _2023.day15.initial

import util.add
import util.removeAt
import util.set
import java.io.File

fun main() {
//    solvePart1() // Solution: 507769, time: 9:18
    solvePart2() // Solution: 269747, time: 23:12
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day15/input/input.txt")
//    val input = File("src/jvmMain/kotlin/day15/input/input_part2_test.txt")
    val line = input.readLines()[0]

    val lensList = line.split(",").map(::Lens)

    val boxList = BoxList()

    lensList.forEachIndexed { index, lens ->
        boxList.update(lens)
        println("Finished iteration: $index, boxList: \n${boxList}")
        println()
    }

    println("Final")
    println(boxList)
    println()

    val result = boxList.value.mapIndexed { index, box ->
        val boxNumber = index + 1
        box.lensList.mapIndexed { slotIndex, lens ->
            val slotNumber = slotIndex + 1
            boxNumber * slotNumber * (lens.focalLength as FocalLength.Available).value
        }.sum()
    }.sum()

    println(result)
}

class BoxList {
    val value = Array(256) { Box(emptyList()) }

    fun update(lens: Lens) {
        when (lens.focalLength) {
            is FocalLength.Available -> {
                val box = value[lens.boxIndex!!]
                val boxLensWithIndex = box.lensList.withIndex().find { (_, boxLens) -> boxLens.name == lens.name }
                val newLensList = if (boxLensWithIndex == null) {
                    // Add lens
                    box.lensList.add(lens)
                } else {
                    // Replace lens
                    val (boxLens, boxLensIndex) = boxLensWithIndex.value to boxLensWithIndex.index
                    box.lensList.set(boxLensIndex, lens)
                }
                value[lens.boxIndex] = Box(newLensList)
            }

            FocalLength.None -> {
//                val box = value[lens.boxIndex!!]
                val boxWithIndex = value.withIndex()
                    .find { (_, box) -> box.lensList.find { boxLens -> boxLens.name == lens.name } != null }
                if (boxWithIndex == null) {
                    // Do nothing
//                    box.lensList
                } else {
                    // Remove lens
                    val boxIndex = boxWithIndex.index
                    val box = value[boxIndex]
                    val lensIndex = box.lensList.withIndex().find { (_, boxLens) -> boxLens.name == lens.name }!!
                    val newLensList = box.lensList.removeAt(lensIndex.index)
                    value[boxIndex] = Box(newLensList)
                }
            }
        }
    }

    override fun toString(): String =
        value.withIndex()
            .filterNot { !it.value.lensList.isNotEmpty() }
            .joinToString("\n") { indexedBox ->
                val box = indexedBox.value
                box.lensList.joinToString(",") { lens -> "${lens.name}=${(lens.focalLength as FocalLength.Available).value}" }
            }


//    override fun toString(): String {
//        return value.withIndex()
//            .fold("") { acc, indexedValue ->
//                if (indexedValue.value.lensList.isEmpty()) {
//                    acc
//                } else {
//                    val box = indexedValue.value
//                    box.lensList.joinToString(", ") { lens ->
//                        when(lens.focalLength) {
//                            is FocalLength.Available -> "${lens.name}=${lens.focalLength.value}"
//                            FocalLength.None -> error("Should not have empty lens. lens: $lens, lensList: ${box.lensList}")
//                        }
//                    } + "; Box #${indexedValue.index}" + "\n"
//                }
//            }
////            .joinToString("\n") { (index, box) -> box.lensList.joinToString(", ") + "; Box #$index" }
//    }
}

data class Box(
    val lensList: List<Lens>
)

fun Lens(text: String): Lens {
    val focalLength = FocalLength(text)
    val (name, boxIndex) = when (focalLength) {
        is FocalLength.Available -> {
            val name = text.split("=")[0]
            name to name.fold(0) { acc, next -> ((acc + next.code) * 17) % 256 }
        }

        FocalLength.None -> {
            text.dropLast(1) to null
        }
    }
    return Lens(
        name = name,
        focalLength = focalLength,
        boxIndex = boxIndex
    )
}

data class Lens(
    val name: String,
    val focalLength: FocalLength,
    val boxIndex: Int?
)

fun FocalLength(text: String): FocalLength =
    if (text.contains("=")) {
        FocalLength.Available(text.split("=")[1].toInt())
    } else {
        FocalLength.None
    }

sealed interface FocalLength {
    data class Available(val value: Int) : FocalLength

    object None : FocalLength
}


fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day15/input/input_part2_test.txt")
    val input = File("src/jvmMain/kotlin/day15/input/input.txt")
    val lines = input.readLines()[0]

    val res = lines.split(",").map { value ->
        value.fold(0) { acc, next ->
            ((acc + next.code) * 17) % 256
        }
    }

    println(res.sum())
}