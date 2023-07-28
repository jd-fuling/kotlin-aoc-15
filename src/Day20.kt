import kotlin.math.sqrt

fun main() {
    fun factors(input: Int): Set<Int> {
        val divisors = mutableSetOf<Int>()

        for (i in 1..sqrt(input.toFloat()).toInt()) {
            if (input % i == 0) {
                divisors.add(i)
                if (i != input / i) {
                    divisors.add(input / i)
                }
            }
        }

        return divisors
    }

    fun part1(input: Int): Int {
        var houseNum = 0
        do {
            houseNum++
            val numPresents = factors(houseNum).fold(0) {acc, i -> acc + i * 10 }
        } while (numPresents < input)

        return houseNum
    }

    fun part2(input: Int): Int {
        var houseNum = 0
        do {
            houseNum++
            val f = factors(houseNum).filter { houseNum / it <= 50 }
            val numPresents = f.fold(0) {acc, i -> acc + i * 11}
        } while (numPresents < input)

        return houseNum
    }

    val input = 36000000
    part1(input).println()
    part2(input).println()
}
