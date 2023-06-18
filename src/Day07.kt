import java.lang.Exception

fun main() {

    data class Source(
        var n: UShort? = null,
        var c1: String? = null,
        var c2: String? = null,
        var operation: String? = null,
        var shift: Int? = null,
    )
    fun getSource(c: String, m: Map<String, Source>, shortCut: MutableMap<String, UShort>): UShort {
        val sc = shortCut[c]
        if (sc != null) {
            return sc
        }
        if (c.toUShortOrNull() != null) {
            return c.toUShort()
        }
        val s = m[c] ?: throw Exception("something bad happened")
        val n = s.n
        if (n != null) {
            return n
        }

        val result = when (s.operation) {
            "NOT" -> getSource(s.c1!!, m, shortCut).inv()
            "AND" -> getSource(s.c1!!, m, shortCut) and getSource(s.c2!!, m, shortCut)
            "OR" -> getSource(s.c1!!, m, shortCut) or getSource(s.c2!!, m, shortCut)
            "LSHIFT" -> (getSource(s.c1!!, m, shortCut).toInt() shl s.shift!!).toUShort()
            "RSHIFT" -> (getSource(s.c1!!, m, shortCut).toInt() ushr s.shift!!).toUShort()
            "NONE" -> getSource(s.c1!!, m, shortCut)
            else -> throw Exception("something bad happened dealing with the operations")
        }
        shortCut[c] = result

        return result
    }

    fun parseInstructions(input: List<String>): Map<String, Source> {
        return input.associate {
            val (left, right) = it.split(" -> ")
            val s = Source()
            if (left.toUShortOrNull() != null) {
                s.n = left.toUShort()
            } else {
                val parts = left.split(" ")
                if (parts[0] == "NOT") {
                    s.operation = parts[0]
                    s.c1 = parts[1]
                } else if (parts.size > 2 && parts[2].toIntOrNull() != null) {
                    s.c1 = parts[0]
                    s.operation = parts[1]
                    s.shift = parts[2].toInt()
                } else if (parts.size == 1) {
                    s.c1 = parts[0]
                    s.operation = "NONE"
                } else {
                    s.c1 = parts[0]
                    s.operation = parts[1]
                    s.c2 = parts[2]
                }
            }

            right to s
        }
    }

    fun part1(input: List<String>): Int {
        val reverseInstructions = parseInstructions(input)

        val shortCut = mutableMapOf<String, UShort>()
        return getSource("a", reverseInstructions, shortCut).toInt()
    }

    fun part2(input: List<String>, override: UShort): Int {
        val reverseInstructions = parseInstructions(input)
        val shortCut = mutableMapOf(
            "b" to override
        )
        return getSource("a", reverseInstructions, shortCut).toInt()
    }

    val input = readInput("Day07")
    val p1result = part1(input)
    p1result.println()
    part2(input, p1result.toUShort()).println()
}
