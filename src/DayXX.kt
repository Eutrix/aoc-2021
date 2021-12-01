import utils.*

fun main() {
    fun part1(input: List<Int>): Int {
        return input.size
    }

    fun part2(input: List<Int>): Int {
        return input.size
    }
    val input = getInput(1,2021).map{ it.toInt() }
    println(part1(input))
    println(part2(input))
}
