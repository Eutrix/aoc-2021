import utils.*
import java.lang.Integer.min
import java.lang.Long.max
import kotlin.math.roundToInt

var die = 1

fun main() {
    fun step(p: Int): Int {
        die += 3
        return (3 * (die + 1) + p) % 10 + 1
    }

    fun part1(input: List<Int>): Int {
        var (p1, p2) = Pair(0, 0)
        var p1p = input.first()
        var p2p = input.last()
        var rolls = 0
        loop@while (true) {
            step(p1p).let {
                p1p = it
                p1 += it
            }

            if (p1 >= 1000) break@loop
            rolls += 1

            step(p2p).let {
                p2p = it
                p2 += it
            }

            if (p2 >= 1000) break@loop
            rolls += 1
        }
        return (rolls + 1) * min(p1, p2) * 3
    }

    fun part2(input: List<Int>): Long {
        // https://www.omnicalculator.com/statistics/dice?v=dice_type_VS:0,dice_type:3,number_of_dice:3,game_option:6.0,target_result:3
        // Šanse da zbroj bude 1, 2, 3, . . .
        val odds = listOf(0.00, 0.00, 0.04, 0.11, 0.22, 0.26, 0.22, 0.11, 0.04)
        val counts = odds.map { (it * 27).roundToInt() } // Count svakog zbroja u 3^3 bacanja
        val p1p = input.first()
        val p2p = input.last()
        var states = mutableMapOf(listOf(p1p,p2p,0,0) to 1L)

        var x = 0L
        var y = 0L
        var turn = true // true za p1, false za p2
        while (states.entries.size > 0) {
            val newStates = mutableMapOf<List<Int>, Long>()
            for (s in states.entries) {
                for (roll in 3..9) {
                    if (turn) {
                        var newX = (s.key.first() + roll) % 10
                        if (newX == 0) newX = 10
                        val score = s.key[2] + newX
                        if (score < 21) {
                            listOf(newX, s.key[1], score, s.key[3]).let {
                                newStates[it] = (newStates[it] ?: 0L) + s.value * counts[roll - 1]
                            }
                        } else x += s.value * counts[roll - 1]
                    } else {
                        var newY = (s.key[1] + roll) % 10
                        if (newY == 0) newY = 10
                        val score = s.key[3] + newY
                        if (score < 21) {
                            listOf(s.key[0], newY, s.key[2], score).let {
                                newStates[it] = (newStates[it] ?: 0) + s.value * counts[roll - 1]
                            }
                        } else y += s.value * counts[roll - 1]
                    }
                }
            }
            states = newStates
            turn = !turn
        }
        return max(x, y)
    }

    fun String.prepareInput(): List<Int> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
            .map{ it.substring(it.length-1, it.length).toInt() }
    }

    val input = getInput(21,2021).prepareInput()
    val testInput = readInput("2021-21-test").prepareInput()

    check(part1(testInput) == 739785)
    die = 1 // Treba vratit da bi pravi input valjao... 🙃
    check(part2(testInput) == 444356092776315)
    println(part1(input))
    println(part2(input))
}
