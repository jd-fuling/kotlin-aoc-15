fun main() {
    fun part1(input: List<String>): Int {
        var floor = 0
        for (c in input.joinToString("")) {
            if (c == '(') {
                floor++
            } else if (c == ')') {
                floor--
            }
        }
        return floor
    }

    fun part2(input: List<String>): Int {
        var floor = 0
        for ((i, c) in input.joinToString("").withIndex()) {
            if (c == '(') {
                floor++
            } else if (c == ')') {
                floor--
            }
            if (floor < 0) {
                return i + 1
            }
        }
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 0)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
