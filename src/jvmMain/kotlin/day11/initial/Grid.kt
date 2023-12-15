package day11.initial

data class Grid<T>(
    // TODO this can be improved by forcing creation by row list or column list.
    //  Now this is ambiguous, but indeded to be created from flattened row list
    private val locationList: List<Location<T>>
) {
    val rowList: List<List<Location<T>>> = locationList.groupBy { it.row }.map { it.value }

    val columnList: List<List<Location<T>>> = locationList.groupBy { it.column }.map { it.value }
}

// TODO
fun <T> Grid<T>.rotate(): Grid<T> {
    return this.columnList.map { it.map { it.copy(row = it.column, column = it.row) } }.flatten().let(::Grid) // Rotation done
        .columnList.flatten().let(::Grid)
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

    val str2 = grid.rotate()/*.toPrintableString(includeLocation = true)*/
    println(str2)
}
