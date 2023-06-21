fun main() {
    fun lookAndSay(input: String): String {
        val result = StringBuilder()
        var current = input[0]
        var n = 1
        for (i in 1..input.lastIndex) {
            val c = input[i]
            if (c == current) {
                n++
                continue
            }
            result.append("$n$current")
            current = c
            n = 1
        }
        result.append("$n$current")

        return result.toString()
    }

    fun iterate(input: String, times: Int): String {
        if (times == 0) {
            return input
        }
        return iterate(lookAndSay(input), times - 1)
    }

    fun part1(input: String): Int {
        val result = iterate(input, 40)
        return result.length
    }

    fun part2(input: String): Int {
        return iterate(input, 50).length
    }

    val input = "1113122113"
    part1(input).println()
    part2(input).println()
}
