fun main() {
    fun surfaceArea(box: List<Int>): Int {
        val (l, w, h) = box
        return 2*l*w + 2*w*h + 2*h*l
    }

    fun dimensions(box: String): List<Int> {
        return box.split("x").map { it.toInt() }
    }

    fun smallestSideArea(box: List<Int>): Int {
        val (l, w) = box.sorted()
        return l * w
    }

    fun part1(input: List<String>): Int {
        return input.map { dimensions(it) }.fold(0) {acc, ints -> acc + surfaceArea(ints) + smallestSideArea(ints)}
    }

    fun part2(input: List<String>): Int {
        return input
            .map { dimensions(it) }
            .map {
                val (l, w, h) = it
                val (smallest, nextSmallest) = it.sorted()
                return@map smallest*2 + nextSmallest*2 + l*w*h
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 58)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
