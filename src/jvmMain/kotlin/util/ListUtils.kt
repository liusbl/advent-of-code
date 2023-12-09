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


fun <T> List<T>.updateLast(value: T): List<T> {
    return this.dropLast(1) + listOf(value)
}

fun <T> List<T>.updateLast(update: T.() -> T): List<T> {
    return updateLast(update(this.last()))
}


fun <T> List<T>.add(value: T): List<T> {
    return this + listOf(value)
}

fun <T> List<T>.add(index: Int, value: T): List<T> {
    return this.toMutableList().apply { add(index, value) }
}

fun <T> List<T>.set(index: Int, value: T): List<T> = toMutableList().apply { set(index, value) }