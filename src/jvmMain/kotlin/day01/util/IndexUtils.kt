package day01.util

fun String.indexOfOrNull(value: String): Int? {
    val index = indexOf(value)
    return if (index == -1) {
        null
    } else {
        index
    }
}

fun <T> List<T>.indexOfOrNull(value: T): Int? {
    val index = indexOf(value)
    return if (index == -1) {
        null
    } else {
        index
    }
}