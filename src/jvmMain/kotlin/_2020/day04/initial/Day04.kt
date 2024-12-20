package _2020.day04.initial

import java.io.File

fun main() {
    // Started:
    // Finished:
    // Solution:
//    solvePart1()

    // Started:dfsadas
    // Finished:
    // Solution:
    solvePart2()
}

fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/_2020/day04/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/_2020/day04/input/input.txt")
    val text = input.readText()

    val mandatory = """
        byr
        iyr
        eyr
        hgt
        hcl
        ecl
        pid
    """.trimIndent().lines()

    val optional = listOf("cid")

    val passportTexts = text.split("\n\n")
    val passports = passportTexts.map { it.replace("\n", " ") }.map { text ->
        text.split(" ")
    }

    val result = passports.count { passport ->
        val fields = passport.map { it.split(":")[0] }
        mandatory.all { fields.contains(it) }
    }

    println(result)
}

// 150 too high
fun solvePart2() {
//    val input = File("src/jvmMain/kotlin/_2020/day04/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/_2020/day04/input/input.txt")
    val text = input.readText()

    val mandatory = listOf(
        "byr" to { text: String -> text.toInt() in 1920..2002 && text.length == 4 },
        "iyr" to { text: String -> text.toInt() in 2010..2020 && text.length == 4 },
        "eyr" to { text: String -> text.toInt() in 2020..2030 && text.length == 4 },
        "hgt" to { text: String ->
            val number = text.dropLast(2)
            if (text.endsWith("cm")) {
                number.toInt() in 150..193
            } else if (text.endsWith("in")) {
                number.toInt() in 59..76
            } else {
                false
            }
        },
        "hcl" to { text: String -> text.startsWith("#") && text.drop(1).all { it.isDigit() || it in 'a'..'f' } },
        "ecl" to { text: String ->
            listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(text)
        },
        "pid" to { text: String -> text.all { it.isDigit() } && text.length == 9 }
    )

    val optional = listOf("cid")

    val passportTexts = text.split("\n\n")
    val passports = passportTexts.map { it.replace("\n", " ") }.map { text ->
        text.split(" ")
    }

    val result = passports.count { passport ->
        val fields = passport.map {
            val (field, value) = it.split(":")
            field to value
        }

        fields.all { (field, value) ->
            if (optional.contains(field)) {
                true
            } else {
                val (field, rule) = mandatory.find { it.first == field } ?: error("$field, $value")
//                println("$field, $value, $rule, ${rule.invoke(value)}")
                rule.invoke(value)
            }
        }
    }

    println(result)
}