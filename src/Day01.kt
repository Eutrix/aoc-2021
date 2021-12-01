import utils.*

fun main() {
    fun part1(input: List<Int>): Int {
        var a = 0
        for (i in 1 until input.size) {
            if (input[i] > input[i-1]) {
                a += 1
            }
        }
        return a
    }

    fun part2(input: List<Int>): Int {
        var b = 0
        for (i in 1 until input.size - 2) {
            if (input.slice(i..i+2).sum() > input.slice(i-1..i+1).sum()) {
                b += 1
            }
        }
        return b
    }
    val input = getInput(1,2021).map{ it.toInt() }
    println(part1(input))
    println(part2(input))
}
