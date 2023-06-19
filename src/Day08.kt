fun main() {

    fun realLength(s: String): Int {
        return s.substring(1, s.lastIndex)
            .replace("""\\""", """\""")
            .replace("""\"""", """"""")
            .replace(Regex("""\\x([a-fA-F0-9]{2})"""), "x")
            .length
    }

    fun part1(input: List<String>): Int {
        val literalLength = input.sumOf { it.length }
        val memoryLength = input.sumOf { realLength(it) }

        return literalLength - memoryLength
    }

    fun escape(s: String): String {
        return "\"" + s
            .replace("\\", "\\\\")
            .replace("\"", "\\\"") + "\""
    }

    fun part2(input: List<String>): Int {
        val encodedSum = input.sumOf { escape(it).length }
        return encodedSum - input.sumOf { it.length }
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
