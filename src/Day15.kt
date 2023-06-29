import kotlin.math.max

fun main() {
    data class Ingredient(
        val name: String,
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int,
    )

    fun Ingredient.totalScore() = this.capacity + this.durability + this.flavor + this.texture

    val pattern = Regex("""^(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""")
    fun parse(s: String): Ingredient {
        val result = pattern.matchEntire(s) ?: throw Exception()
        val (name, capacity, durability, flavor, texture, calories) = result.destructured

        return Ingredient(
            name,
            capacity.toInt(),
            durability.toInt(),
            flavor.toInt(),
            texture.toInt(),
            calories.toInt(),
        )
    }

    fun calorieCount(cookieMix: List<Pair<Ingredient, Int>>): Int {
        return cookieMix.fold(0) { acc, pair -> acc + pair.first.calories * pair.second }
    }

    fun scoreOfCookieMix(cookieMix: List<Pair<Ingredient, Int>>): Int {
        val capacity = max(cookieMix.sumOf { it.first.capacity * it.second }, 0)
        val durability = max(cookieMix.sumOf { it.first.durability * it.second }, 0)
        val flavor = max(cookieMix.sumOf { it.first.flavor * it.second }, 0)
        val texture = max(cookieMix.sumOf { it.first.texture * it.second }, 0)

        return capacity * durability * flavor * texture
    }

    fun calculateStarsAndBarsPermutations(stars: Int, bars: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        fun recurse(n: Int, k: Int, current: List<Int>) {
            if (k == 0) {
                result.add(current.plus(n))
                return
            }

            for (i in k .. n-k) {
                val firstSlot = n - i
                val rest = n - firstSlot
                recurse(rest, k - 1, current.plus(firstSlot))
            }
        }

        recurse(stars, bars, listOf())

        return result
    }

    fun part1(input: List<String>): Int {
        val spoonFulls = 100
        val ingredients = input.map { parse(it) }.sortedByDescending { it.totalScore() }
        val permutations = calculateStarsAndBarsPermutations(spoonFulls, ingredients.size - 1)
        var bestScore = 0
        for (p in permutations) {
            val mixture = p.mapIndexed {index, spoons -> Pair(ingredients[index], spoons) }
            scoreOfCookieMix(mixture).let {
                if (it > bestScore) {
                    println("""mix is better: $mixture ($it)""")
                    bestScore = it
                }
            }
        }
        return bestScore
    }


    fun part2(input: List<String>): Int {
        val spoonFulls = 100
        val ingredients = input.map { parse(it) }.sortedByDescending { it.totalScore() }
        val permutations = calculateStarsAndBarsPermutations(spoonFulls, ingredients.size - 1)
        var bestScore = 0
        for (p in permutations) {
            val mixture = p.mapIndexed {index, spoons -> Pair(ingredients[index], spoons) }
            scoreOfCookieMix(mixture).let {
                if (it > bestScore && calorieCount(mixture) == 500) {
                    println("""better 500 calorie mix: $mixture ($it)""")
                    bestScore = it
                }
            }
        }
        return bestScore
    }

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
