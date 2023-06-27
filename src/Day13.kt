import java.lang.Exception

fun main() {

    val pattern = Regex("""^(\w+) would (lose|gain) (\d+) happiness units by sitting next to (\w+)\.$""")
    fun parse(lines: List<String>): MutableMap<String, MutableMap<String, Int>> {
        val result = mutableMapOf<String, MutableMap<String, Int>>()
        for (line in lines) {
            val match = pattern.matchEntire(line) ?: throw Exception()
            val (_, left, sign, happiness, right) = match.groups.toList().map { it?.value ?: throw Exception() }
            result.getOrPut(left){ mutableMapOf() }[right] = when (sign) {
                "gain" -> happiness.toInt()
                "lose" -> happiness.toInt() * -1
                else -> throw Exception()
            }
        }

        return result
    }

    fun sumHappiness(lookup: Map<String, Map<String, Int>>, arrangement: List<String>): Int {
        var sum = 0
        for (i in 0..arrangement.lastIndex) {
            val person = arrangement[i]
            val toTheLeft = if (i == 0) arrangement.last() else arrangement[i - 1]
            val toTheRight = if (i == arrangement.lastIndex) arrangement.first() else arrangement[i + 1]

            val leftHappiness = lookup[person]?.get(toTheLeft) ?: throw Exception("end of table??")
            val rightHappiness = lookup[person]?.get(toTheRight) ?: throw Exception("sitting alone to the right...")

            sum += leftHappiness + rightHappiness
        }

        return sum
    }

    fun permutations(list: List<String>): List<List<String>> {
        val result = mutableListOf<List<String>>()
        fun recurse(current: List<String>, remaining: List<String>) {
            if (remaining.isEmpty()) {
                result.add(current)
                return
            }
            for (i in 0..remaining.lastIndex) {
                val nextPermutation = current.plus(remaining[i])
                val remainingElements = remaining.filterIndexed { ii, _ -> i != ii }
                recurse(nextPermutation, remainingElements)
            }
        }
        recurse(listOf(), list)

        return result
    }

    fun maxHappiness(lookup: Map<String, Map<String, Int>>): Int {
        val possibleArrangements = permutations(lookup.keys.toList())
        var maxHappiness = 0
        for (a in possibleArrangements) {
            val happiness = sumHappiness(lookup, a)
            if (happiness > maxHappiness) {
                maxHappiness = happiness
                println("$a is better (happiness of $happiness)")
            }
        }

        return maxHappiness
    }

    fun part1(input: List<String>): Int {
        val lookup = parse(input)
        return maxHappiness(lookup)
    }

    fun part2(input: List<String>): Int {
        val lookup = parse(input)
        for ((_, v) in lookup) {
            v["myself"] = 0
        }
        lookup["myself"] = lookup.keys.associateWith { 0 }.toMutableMap()
        return maxHappiness(lookup)
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
