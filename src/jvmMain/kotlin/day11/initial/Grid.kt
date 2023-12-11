package day11.initial

data class Grid<T>(
    val locationList: List<Location<T>>
) {
    val rowList: List<List<Location<T>>> = locationList.groupBy { it.row }.map { it.value }
}