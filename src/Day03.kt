fun main() {

    data class Coordinate(var x: Int, var y: Int)
    fun Coordinate.move(dir: Char) {
        when (dir) {
            '^' -> this.y++
            'v' -> this.y--
            '>' -> this.x++
            '<' -> this.x--
        }
    }

    fun part1(input: List<String>): Int {
        val s = mutableSetOf<Coordinate>()
        val currentLocation = Coordinate(0, 0)
        s.add(currentLocation.copy())
        for (c in input.joinToString("")) {
            s.add(currentLocation.apply { move(c) }.copy())
        }
        return s.size
    }

    fun part2(input: List<String>): Int {
        val s = mutableSetOf<Coordinate>()
        val santaLocation = Coordinate(0, 0)
        val robotLocation = Coordinate(0, 0)
        s.add(santaLocation.copy())
        for ((i, c) in input.joinToString("").withIndex()) {
            val currentLocation = if (i % 2 == 0) santaLocation else robotLocation
            s.add(currentLocation.apply { move(c) }.copy())
        }
        return s.size
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
