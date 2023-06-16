import java.lang.Exception

enum class Day06LightOperation {
    ON,
    TOGGLE,
    OFF
}

fun main() {
    data class Coordinate(
        val x: Int,
        val y: Int
    )

    data class Instruction(
        val operation: Day06LightOperation,
        val corner1: Coordinate,
        val corner2: Coordinate,
    )

    val r = Regex("""(turn on|toggle|turn off) (\d+,\d+) through (\d+,\d+)""")
    fun parseInstruction(s: String): Instruction {
        val match = r.matchEntire(s) ?: throw Exception("Failed to parse instruction")
        val operation = when (match.groups[1]!!.value) {
            "turn off" -> Day06LightOperation.OFF
            "toggle" -> Day06LightOperation.TOGGLE
            "turn on" -> Day06LightOperation.ON
            else -> throw Exception("unknown operation")
        }
        val (x1,y1) = match.groups[2]!!.value.split(",").map { it.toInt() }
        val (x2,y2) = match.groups[3]!!.value.split(",").map { it.toInt() }

        return Instruction(operation, Coordinate(x1, y1), Coordinate(x2, y2))
    }

    fun part1(input: List<String>): Int {
        val grid = MutableList(1000) { MutableList(1000) { false } }
        for (s in input) {
            val instruction = parseInstruction(s)
            val xRange = instruction.corner1.x .. instruction.corner2.x
            val yRange = instruction.corner1.y .. instruction.corner2.y

            for (x in yRange) {
                for (y in xRange) {
                    grid[y][x] = when (instruction.operation) {
                        Day06LightOperation.ON -> true
                        Day06LightOperation.OFF -> false
                        Day06LightOperation.TOGGLE -> !grid[y][x]
                    }
                }
            }
        }
        return grid.sumOf { y -> y.filter { x -> x }.size }
    }

    fun part2(input: List<String>): Int {
        val grid = MutableList(1000) { MutableList(1000) { 0 } }
        for (s in input) {
            val instruction = parseInstruction(s)
            val xRange = instruction.corner1.x .. instruction.corner2.x
            val yRange = instruction.corner1.y .. instruction.corner2.y

            for (x in yRange) {
                for (y in xRange) {
                    grid[y][x] = when (instruction.operation) {
                        Day06LightOperation.ON -> grid[y][x] + 1
                        Day06LightOperation.OFF -> (grid[y][x] - 1).coerceAtLeast(0)
                        Day06LightOperation.TOGGLE -> grid[y][x] + 2
                    }
                }
            }
        }
        return grid.sumOf { y -> y.sum() }
    }

    // test if implementation meets criteria from the description, like:
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
