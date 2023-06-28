import kotlin.math.min

const val DAY_14_RACE_TIME = 2503
fun main() {
    data class Reindeer(
        val name: String,
        val speed: Int,
        val fuel: Int,
        val restingTime: Int,
        var points: Int = 0,
    )

    fun Reindeer.distanceAfterSeconds(s: Int): Int {
        val fullCycles = s / (this.fuel + this.restingTime)
        val remaining = s - (this.fuel + this.restingTime) * fullCycles

        return fullCycles * this.speed * this.fuel +
                min(remaining, fuel) * this.speed
    }

    val pattern = Regex("""^(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.$""")
    fun parse(input: String): Reindeer {
        val result = pattern.matchEntire(input) ?: throw Exception()
        val (name, speed, fuel, restingTime) = result.destructured

        return Reindeer(
            name,
            speed.toInt(),
            fuel.toInt(),
            restingTime.toInt()
        )
    }

    fun part1(input: List<String>): Int {
        val reindeer = input.map { parse(it) }
        return reindeer.maxOf { it.distanceAfterSeconds(DAY_14_RACE_TIME) }
    }

    fun part2(input: List<String>): Int {
        val reindeer = input.map { parse(it) }
        for (s in 1..DAY_14_RACE_TIME) {
            val leadingDistance = reindeer.maxOf { it.distanceAfterSeconds(s) }
            reindeer.filter { it.distanceAfterSeconds(s) == leadingDistance }.forEach {
                it.points++
            }
        }

        return reindeer.maxOf { it.points }
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
