fun main() {
    val vowels = "aeiou"
    val blackList = listOf<String>(
        "ab",
        "cd",
        "pq",
        "xy"
    )

    fun isNicePart1(s: String): Boolean {
        if (s.filter { c -> vowels.contains(c) }.length < 3) {
            return false
        }
        var hasRepeatChar = false
        for (i in 1..s.lastIndex) {
            val left = s[i-1]
            val right = s[i]
            if (left == right) {
                hasRepeatChar = true
            }
            if (blackList.contains("$left$right")) {
                return false
            }
        }

        return hasRepeatChar
    }

    fun part1(input: List<String>): Int {
        var niceCount = 0
        for (s in input) {
            if (isNicePart1(s)) {
                niceCount++
            }
        }

        return niceCount
    }

    fun containsPair(s: String): Boolean {
        for (i in 1 until s.length) {
            val pair = s.substring(i-1, i+1)
            if (s.indexOf(pair, i+1) >= 0) {
                return true
            }
        }

        return false
    }

    fun hasRepeatedLetter(s: String): Boolean {
        for (i in 2..s.lastIndex) {
            val left = s[i-2]
            val right = s[i]
            if (left == right) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>): Int {
        var niceCount = 0
        for (s in input) {
            if (containsPair(s) && hasRepeatedLetter(s)) {
                niceCount++
            }
        }
        return niceCount
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
