fun main() {
    fun findCombinations(nogAmount: Int, containers: List<Int>, currentCombination: Int, allCombinations: MutableList<Int>) {
        if (containers.isEmpty()) {
            return
        }

        for (i in 0..containers.lastIndex) {
            val containerSize = containers[i]
            val remainingNog = nogAmount - containerSize
            if (remainingNog == 0) {
                allCombinations.add(currentCombination + 1)
            } else if (remainingNog < 0) {
                continue
            } else {
                findCombinations(remainingNog, containers.subList(i + 1, containers.lastIndex + 1), currentCombination + 1, allCombinations)
            }
        }
    }

    val amountOfNog = 150
    fun part1(input: List<String>): Int {
        val containers = input.map { it.toInt() }.sortedDescending()
        val combinations = mutableListOf<Int>()
        findCombinations(amountOfNog, containers, 0, combinations)

        return combinations.size
    }

    fun part2(input: List<String>): Int {
        val containers = input.map { it.toInt() }.sortedDescending()
        val combinations = mutableListOf<Int>()
        findCombinations(amountOfNog, containers, 0, combinations)

        val minimumContainers = combinations.min()

        return combinations.filter { it == minimumContainers }.size
    }

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
