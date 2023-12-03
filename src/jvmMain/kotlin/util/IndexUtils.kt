package util

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

fun <T> List<T>.set(index: Int, value: T): List<T> = toMutableList().apply { set(index, value) }