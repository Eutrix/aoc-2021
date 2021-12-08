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
    val testInput = readInput("2021-1-test").prepareInput()

    //check(part1(testInput) == 37)
    //check(part2(testInput) == 168)
    println(part1(testInput))
    println(part2(testInput))

    println(part1(input))
    println(part2(input))
}
