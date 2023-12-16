package day11.initial

import util.set

data class Grid<T>(
    // TODO this can be improved by forcing creation by row list or column list.
    //  Now this is ambiguous, but indeded to be created from flattened row list
    private val locationList: List<Location<T>>
) {
    val rowList: List<List<Location<T>>> = locationList.groupBy { it.row }.map { it.value }

    val columnList: List<List<Location<T>>> = locationList.groupBy { it.column }.map { it.value }
}

fun <T> Grid<T>.inBounds(row: Int, column: Int): Boolean =
    row >= 0 &&
            column >= 0 &&
            row < rowList.size &&
            column < columnList.size

fun <T> Grid<T>.move(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int, filledValue: T, replaceValue: (oldValue: T) -> T): Grid<T> {
    return update(fromRow, fromColumn) { filledValue }
        .run {
            if (inBounds(toRow, toColumn)) {
                val toValue = rowList[toRow][toColumn].value
                update(toRow, toColumn) { replaceValue(toValue) }
            } else {
                this
            }
        }
}

fun <T> Grid<T>.update(row: Int, column: Int, transform: (T) -> T): Grid<T> {
    val location = rowList[row][column]
    val newRow = rowList[row].set(column, location.copy(value = transform(location.value)))
    val newRowList = rowList.set(row, newRow)
    return Grid(newRowList.flatten())
}

// TODO
fun <T> Grid<T>.rotate(): Grid<T> {
    return this.columnList.map { it.map { it.copy(row = it.column, column = it.row) } }.flatten().let(::Grid)
}

fun <T> Grid<T>.toPrintableString(includeLocation: Boolean): String =
    if (includeLocation) {
        rowList.joinToString(separator = "\n") { row ->
            row.joinToString(separator = " | ") { "${it.value},${it.row},${it.column}" }
        }
    } else {
        rowList.joinToString(separator = "\n") { row ->
            row.joinToString(separator = "") { location -> location.toPrintableString() }
        }
    }

fun main() {
    val grid = Grid<Int>(
        listOf(
            Location(1, 0, 0), Location(2, 0, 1), Location(3, 0, 2), Location(4, 0, 3),
            Location(5, 1, 0), Location(6, 1, 1), Location(7, 1, 2), Location(8, 1, 3),
            Location(9, 2, 0), Location(10, 2, 1), Location(11, 2, 2), Location(12, 2, 3)
        )
    )

    val str = grid.toPrintableString(includeLocation = true)
    println(str)
    println()

    println("Rotated")
    val str2 = grid.rotate()/*.toPrintableString(includeLocation = true)*/
    println(str2.toPrintableString(includeLocation = true))
}
