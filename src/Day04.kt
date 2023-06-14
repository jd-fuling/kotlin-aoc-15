import java.nio.charset.Charset
import java.security.MessageDigest

fun main() {

    val md = MessageDigest.getInstance("MD5")

    fun md5(input: String): ByteArray = md.digest(input.toByteArray(Charset.forName("UTF-8")))

    fun ByteArray.toHex() = joinToString("") { byte -> "%02x".format(byte) }

    fun bruteForceIt(input: String, needsToStartWith: String): Int {
        var currentNumber = 0
        while (true) {
            if (md5("$input$currentNumber").toHex().startsWith(needsToStartWith)) {
                return currentNumber
            }
            currentNumber++
        }
    }

    fun part1(input: List<String>): Int {
        return bruteForceIt(input.first(), "".padStart(5, '0'))
    }

    fun part2(input: List<String>): Int {
        return bruteForceIt(input.first(), "".padStart(6, '0'))
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
