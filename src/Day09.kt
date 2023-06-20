import java.lang.Exception

fun main() {
    data class Edge(
        val weight: Int,
        val connected: String
    )

    fun buildGraph(input: List<String>): Map<String, List<Edge>> {
        val graph = mutableMapOf<String, MutableList<Edge>>()
        for (s in input) {
            val (route, weight) = s.split(" = ")
            val (from, to) = route.split(" to ")

            graph.getOrPut(from) { mutableListOf() }.add(Edge(weight.toInt(), to))
            graph.getOrPut(to) { mutableListOf() }.add(Edge(weight.toInt(), from))
        }

        return graph
    }

    fun findPaths(graph: Map<String, List<Edge>>, current: String, visited: MutableList<String>, paths: MutableList<List<String>>, maxLength: Int) {
        if (visited.contains(current)) {
            return
        }
        visited.add(current)

        if (visited.size == maxLength) {
            paths.add(visited)
            return
        }

        val edges = graph[current] ?: throw Exception("whoops")
        for (edge in edges) {
            val visitedCopy = mutableListOf<String>()
            visitedCopy.addAll(visited)
            findPaths(graph, edge.connected, visitedCopy, paths, maxLength)
        }
    }

    fun costOfPath(graph: Map<String, List<Edge>>, path: List<String>): Int {
        var sum = 0
        val start = path[0]
        var edges = graph[start] ?: throw Exception("bad")
        for (i in 1..path.lastIndex) {
            val edge = edges.find { it.connected == path[i] }
            sum += edge!!.weight
            edges = graph[edge.connected]!!
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        val graph = buildGraph(input)

        var cheapestCost = Int.MAX_VALUE
        var cheapestPath: List<String>? = null
        for (t in graph.keys) {
            val visited = mutableListOf<String>()
            val paths = mutableListOf<List<String>>()
            findPaths(graph, t, visited, paths, graph.keys.size)
            for (p in paths) {
                val cost = costOfPath(graph, p)
                if (cost < cheapestCost) {
                    cheapestPath = p
                    cheapestCost = cost
                }
            }
        }

        println("cheapest path: $cheapestPath ($cheapestCost)")

        return cheapestCost
    }

    fun part2(input: List<String>): Int {
        val graph = buildGraph(input)

        var highestCost = 0
        var selectedPath: List<String>? = null
        for (t in graph.keys) {
            val paths = mutableListOf<List<String>>()
            findPaths(graph, t, mutableListOf(), paths, graph.keys.size)
            for (p in paths) {
                val cost = costOfPath(graph, p)
                if (cost > highestCost) {
                    selectedPath = p
                    highestCost = cost
                }
            }

        }

        println("longest path: $selectedPath ($highestCost)")

        return highestCost
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 605)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
