import kotlinx.serialization.json.*

fun main() {

    fun sum(j: JsonElement, blacklist: String?): Int {
        if (j is JsonPrimitive && !j.isString) {
            return j.content.toInt()
        }
        if (j is JsonArray) {
            return j.fold(0) { acc, jsonElement -> acc + sum(jsonElement, blacklist)}
        }
        if (j is JsonObject) {
            if (blacklist != null && j.values.find { it is JsonPrimitive && it.isString && it.content == blacklist } != null) {
                return 0
            }
            return j.values.fold(0) { acc, jsonElement -> acc + sum(jsonElement, blacklist) }
        }
        return 0
    }

    fun part1(input: String): Int {
        val json = Json.parseToJsonElement(input)
        return sum(json, null)
    }

    fun part2(input: String): Int {
        val json = Json.parseToJsonElement(input)
        return sum(json, "red")
    }

    val input = readInput("Day12")[0]
    part1(input).println()
    part2(input).println()
}
