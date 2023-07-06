fun main() {

    fun parse(input: List<String>): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        for (line in input) {
            val (molecule, replacement) = line.split(" => ")
            result.getOrPut(molecule) { mutableListOf() }.add(replacement)
        }

        return result
    }

    fun part1(input: List<String>): Int {
        val replacements = parse(input.dropLast(2))
        val calibrationString = input.last()

        val distinctReplacements = mutableSetOf<String>()

        var i = 0
        while (i < calibrationString.length) {
            val c1 = calibrationString[i]
            val c2 = if (i < calibrationString.lastIndex) calibrationString[i+1] else null

            if (replacements.containsKey("$c1$c2")) {
                for (replacement in replacements["$c1$c2"]!!) {
                    distinctReplacements.add(
                        calibrationString.substring(0 until i)
                        + replacement
                        + calibrationString.substring(i+2)
                    )
                }
                i += 2
                continue
            }
            if (replacements.containsKey("$c1")) {
                for (replacement in replacements["$c1"]!!) {
                    distinctReplacements.add(
                        calibrationString.substring(0 until i)
                                + replacement
                                + calibrationString.substring(i+1)
                    )
                }
            }

            i++
        }

        println(distinctReplacements)

        return distinctReplacements.size
    }

    fun part2(input: List<String>): Int {
        return 42
    }

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
