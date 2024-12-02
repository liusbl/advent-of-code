package day07.initial

import java.io.File

fun main() {
//    solvePart1() // Solution: 253910319, time: 08:02
    solvePart2() // Solution: 253910319, time: 08:02
}

fun solvePart2() {
    val input = File("src/jvmMain/kotlin/day07/input/input_part1_test.txt")
//    val input = File("src/jvmMain/kotlin/day07/input/input.txt")
    val lines = input.readLines()

    val handList = lines.map {
        it.split(" ")
            .filter { it.isNotBlank() }
    }.map { (handText, bidText) ->
        Hand(handText, bidText)
    }.sorted()

    val message = handList.mapIndexed { index, hand ->
        "${hand.list.map { it.char }.joinToString("")} ${hand.bid} ${(index + 1)} ${hand.type}"
    }

    println(message.joinToString("\n"))
    println(handList.mapIndexed { index, hand -> hand.bid * (index + 1)}.sum())
}


// 254046151 is not right answer
fun solvePart1() {
//    val input = File("src/jvmMain/kotlin/day07/input/input_part1_test.txt")
    val input = File("src/jvmMain/kotlin/day07/input/input.txt")
    val lines = input.readLines()

    val handList = lines.map {
        it.split(" ")
            .filter { it.isNotBlank() }
    }.map { (handText, bidText) ->
        Hand(handText, bidText)
    }.sorted()

    val message = handList.mapIndexed { index, hand ->
        "${hand.list.map { it.char }.joinToString("")} ${hand.bid} ${(index + 1)} ${hand.type}"
    }

    println(message.joinToString("\n"))
    println(handList.mapIndexed { index, hand -> hand.bid * (index + 1)}.sum())
}


fun Hand(handText: String, bidText: String): Hand {
    return Hand(
        list = handText.map { handChar -> Card.values().first { it.char == handChar } },
        type = Type.HighCard,
        bid = bidText.toInt()
    ).let { hand ->
        hand.copy(type = Type.values().reversed().first { it.rule(hand) })
    }
}

data class Hand(
    val list: List<Card>, // 5 cards
    val type: Type,
    val bid: Int
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        val firstRuleResult = compareByFirstRule(other)
        return if (firstRuleResult == 0) {
            compareBySecondRule(other)
        } else {
            firstRuleResult
        }
    }

    private fun compareByFirstRule(other: Hand): Int {
        val rules = Type.values().reversed()
        val typeFirst = rules.find { it.rule(this) }!!
        val typeOther = rules.find { it.rule(other) }!!
        return typeFirst.ordinal.compareTo(typeOther.ordinal)
    }

    private fun compareBySecondRule(other: Hand): Int {
        val pairs = this.list.zip(other.list) { first, second ->
            first to second
        }

        val pair = pairs.find { pair ->
            pair.first != pair.second
        } ?: error("Why are pairs the same? this hand: $this, other hand: $other, pairs: $pairs")

        return if (pair.first > pair.second) {
            1
        } else {
            -1
        }
    }
}

enum class Type(val rule: (hand: Hand) -> Boolean) {
    HighCard({ hand -> hand.list.distinct().size == 5 }),
    OnePair({ hand -> hand.list.distinct().size == 4 }),
    TwoPair({ hand -> hand.list.distinct().size == 3 }),
    ThreeOfAKind({ hand -> hand.list.sortedBy { it.char }.windowed(3).any { it.distinct().size == 1 } }),
    FullHouse({ hand ->
        hand.list.distinct().size == 2 &&
                hand.list.sortedBy { it.char }.windowed(3).any { it.distinct().size == 1 }
    }),
    FourOfAKind({ hand -> hand.list.distinct().size == 2 &&
            hand.list.sortedBy { it.char }.windowed(4).any { it.distinct().size == 1 }}),
    FiveOfAKind({ hand -> hand.list.distinct().size == 1 });
}

// Throws stack overflow for some reason
//enum class Type(val rule: (hand: Hand) -> Boolean) {
//    HighCard({ hand ->
//        replaceJoker(hand).list.distinct().size == 5
//    }),
//    OnePair({ hand -> replaceJoker(hand).list.distinct().size == 4 }),
//    TwoPair({ hand -> replaceJoker(hand).list.distinct().size == 3 }),
//    ThreeOfAKind({ hand -> replaceJoker(hand).list.sortedBy { it.char }.windowed(3).any { it.distinct().size == 1 } }),
//    FullHouse({ hand ->
//        val replaced = replaceJoker(hand)
//        replaced.list.distinct().size == 2 &&
//                replaced.list.sortedBy { it.char }.windowed(3).any { it.distinct().size == 1 }
//    }),
//    FourOfAKind({ hand ->
//        val replaced = replaceJoker(hand)
//        replaced.list.distinct().size == 2 &&
//                replaced.list.sortedBy { it.char }.windowed(4).any { it.distinct().size == 1 }}),
//    FiveOfAKind({ hand -> replaceJoker(hand).list.distinct().size == 1 });
//
//
//}

fun replaceJoker(hand: Hand): Hand {
    return if (hand.list.any { it == Card.Joker }) {
        // Create all possible hands with Joker replaced
        val handList = Card.values().map { possibleCard ->
            val newList = hand.list.map { handCard -> if (handCard == Card.Joker) possibleCard else handCard }
            Hand(newList, Type.HighCard, hand.bid).run { copy(type = Type.values().reversed().first { it.rule(hand) })}
        }.sortedByDescending { it }[0]
        handList
    } else {
        hand
    }
}


fun Hand.compareByFirst(other: Hand): Int {
    val fiveOfAKind = other
    return 0
}

fun Hand.compareBySecond(other: Hand): Int {
    return 0
}

enum class Card(val char: Char, val value: Int) {
    Joker('J', 1),
    Two('2', 2),
    Three('3', 3),
    Four('4', 4),
    Five('5', 5),
    Six('6', 6),
    Seven('7', 7),
    Eight('8', 8),
    Nine('9', 9),
    Ten('T', 10),
    Queen('Q', 12),
    King('K', 13),
    Ace('A', 14);
}