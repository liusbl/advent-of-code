package day11.initial

data class Grid<T>(
    // TODO this can be improved by forcing creation by row list or column list. Now this is ambiguous
    private val locationList: List<Location<T>>
) {
    val rowList: List<List<Location<T>>> = locationList.groupBy { it.row }.map { it.value }

    val columnList: List<List<Location<T>>> = locationList.groupBy { it.column }.map { it.value }
}