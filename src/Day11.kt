fun main() {

    fun increment(arr: CharArray): CharArray {
        if (arr.isEmpty()) {
            return arr
        }
        arr[arr.lastIndex]++
        if (arr[arr.lastIndex] > 'z') {
            if (arr.size == 1) {
                return charArrayOf('a', 'a')
            }
            arr[arr.lastIndex] = 'a'
            return increment(arr.slice(0 until arr.lastIndex).toCharArray()).plus(arr[arr.lastIndex])
        }
        return arr
    }

    fun nextPassword(input: String): String {
        return increment(input.toCharArray()).joinToString("")
    }

    fun hasIncreasingStraight(password: String): Boolean {
        for (i in 0..password.lastIndex - 2) {
            if (password[i].code == password[i + 1].code - 1 && password[i + 1].code == password[i + 2].code - 1) {
                return true
            }
        }

        return false
    }

    fun hasDoubleOverlapping(password: String): Boolean {
        val doubleIndices = mutableListOf<Int>()
        for (i in 0 until password.lastIndex) {
            if (password[i] == password[i + 1]) {
                doubleIndices.add(i)
            }
        }
        if (doubleIndices.size < 2) {
            return false
        }
        if (doubleIndices.size > 2) {
            return true
        }
        val (first, second) = doubleIndices
        return second - first > 1
    }

    val blacklistedChars = listOf('i', 'o', 'l')

    fun isValid(password: String): Boolean {
        return hasIncreasingStraight(password) && password.toCharArray()
            .all { !blacklistedChars.contains(it) } && hasDoubleOverlapping(password)
    }

    fun nextValidPassword(password: String): String {
        var currentPw = nextPassword(password)
        while (!isValid(currentPw)) {
            currentPw = nextPassword(currentPw)
        }

        return currentPw
    }

    fun part1(input: String): String {
        return nextValidPassword(input)
    }

    fun part2(input: String): String {
        return nextValidPassword(nextValidPassword(input))
    }

    val input = "cqjxjnds"
    part1(input).println()
    part2(input).println()
}
