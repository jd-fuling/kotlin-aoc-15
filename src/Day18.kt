fun main() {

    data class Point(val x: Int, val y: Int)

    fun neighbors(coord: Point): List<Point> {
        return listOf(
            Point(coord.x-1, coord.y-1),    Point(coord.x, coord.y-1),     Point(coord.x+1, coord.y-1),
            Point(coord.x-1, coord.y),                                          Point(coord.x+1, coord.y),
            Point(coord.x-1, coord.y+1),    Point(coord.x, coord.y+1),     Point(coord.x+1, coord.y+1)
        )
    }

    fun cycle(list: List<String>, alwaysOnLights: List<Point>? = null): List<String> {
        val result = list.toList().map { it.toCharArray() }
        for (r in 0..list.lastIndex) {
            for (c in 0..list[r].lastIndex) {
                if (alwaysOnLights != null && alwaysOnLights.contains(Point(c, r))) {
                    result[r][c] = '#'
                    continue
                }
                val n = neighbors(Point(c, r))
                val onNeighbors = n.filter { it.x in 0..list[r].lastIndex && it.y in 0..list.lastIndex }
                    .count { list[it.y][it.x] == '#' || (alwaysOnLights != null && alwaysOnLights.contains(it)) }

                if (list[r][c] == '#') {
                    result[r][c] = if(onNeighbors in 2..3) '#' else '.'
                } else if (list[r][c] == '.') {
                    result[r][c] = if(onNeighbors == 3) '#' else '.'
                }
            }
        }
        return result.map { it.concatToString() }
    }

    fun part1(input: List<String>): Int {
        var game = input
        println(game.joinToString("\n"))
        repeat(100) {
            game = cycle(game)
            println(game.joinToString("\n"))
        }
        return game.sumOf { it.count { c -> c == '#' } }
    }

    fun part2(input: List<String>): Int {
        var game = input
        println(game.joinToString("\n"))
        repeat(100) {
            game = cycle(game, listOf(
                Point(0, 0),
                Point(99, 0),
                Point(0, 99),
                Point(99, 99),
            ))
            println(game.joinToString("\n"))
        }
        return game.sumOf { it.count { c -> c == '#' } }
    }

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
