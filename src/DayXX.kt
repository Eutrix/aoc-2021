import utils.*

fun main() {
    fun part1(input: List<Int>): Int {
        return input.size
    }

    fun part2(input: List<Int>): Int {
        return input.size
    }

    fun String.prepareInput(): List<Int> {
        return this.split("\n")
            .map{ it.toInt() }
    }

    val input = getInput(1,2021).prepareInput()

    println(part1(input))
    println(part2(input))
}
