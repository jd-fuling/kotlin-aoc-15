fun main() {

    fun parse(lines: List<String>): Map<Int, Map<String, Int>> {
        val result = mutableMapOf<Int, Map<String, Int>>()
        for (line in lines) {
            val parts = line.replace(":", "").replace(",", "").split(" ")
            val auntNum = parts[1].toInt()

            result[auntNum] = parts.drop(2).zipWithNext().filterIndexed { index, _ -> index % 2 == 0 }
                .associate { it.first to it.second.toInt() }
        }

        return result
    }

    val tickerTape = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1,
    )

    fun part1(input: List<String>): Int {
        val aunts = parse(input)

        return aunts.entries.find { auntEntry ->
            auntEntry.value.entries.all { auntStat ->
                tickerTape.containsKey(auntStat.key) && tickerTape[auntStat.key] == auntStat.value
            }
        }?.key ?: throw Exception("no aunt found")
    }

    fun part2(input: List<String>): Int {
        val aunts = parse(input)
        return aunts.entries.find { auntEntry ->
            auntEntry.value.entries.all { auntStat ->
                if (!tickerTape.containsKey(auntStat.key)) {
                    return@all false
                }

                return@all when (auntStat.key) {
                    "cats", "trees" -> tickerTape[auntStat.key]!! < auntStat.value
                    "pomeranians", "goldfish" -> tickerTape[auntStat.key]!! > auntStat.value
                    else -> tickerTape[auntStat.key]!! == auntStat.value
                }
            }
        }?.key ?: throw Exception("no aunt found")
    }

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
