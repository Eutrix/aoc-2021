import utils.*
import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>): Int {
        fun sumOfResiduals(input: List<Int>, n: Int): Int {
            return input.sumOf { abs(it - n) }
        }

        var min = Int.MAX_VALUE
        for ( i in 1..(input.maxOrNull() ?: 0) ) {
            val fuel = sumOfResiduals(input, i)
            if (fuel < min) min = fuel
        }

        return min
    }

    fun part2(input: List<Int>): Int {
        fun sumOfResiduals(input: List<Int>, n: Int): Int {
            return input.sumOf { abs(it - n) * (abs(it - n) + 1) / 2 }
        }

        var min = Int.MAX_VALUE
        for ( i in 1..(input.maxOrNull() ?: 0) ) {
            val fuel = sumOfResiduals(input, i)
            if (fuel < min) min = fuel
        }
        return min
    }

    fun String.prepareInput(): List<Int> {
        return this.trimIndent()
            .split(",")
            .map{ it.toInt() }
    }

    val input = getInput(7,2021).prepareInput()
    val testInput = readInput("2021-7-test").prepareInput()

    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    println(part1(input))
    println(part2(input))
}
