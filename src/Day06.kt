import utils.*

fun main() {
    fun part1(input: List<Int>, cycles: Int = 80): Long {
        fun MutableMap<Int,Long>.getGood(n: Int): Long {
            return this.getOrDefault(n, 0)
        }

        val fishAtStage = mutableMapOf<Int,Long>()
        for (i in 0..8) {
            fishAtStage[i] = input.count { it == i }.toLong()
        }

        fun runCycle() {
            val newTimeMap = mutableMapOf<Int, Long>()
            for (i in 0..7) {
                newTimeMap[i] = fishAtStage.getGood(i + 1)
            }
            newTimeMap[6] = fishAtStage.getGood(7) + fishAtStage.getGood(0)
            newTimeMap[8] = fishAtStage.getGood(0)
            for (i in 0..8) {
                fishAtStage[i] = newTimeMap.getGood(i)
            }
        }

        repeat(cycles) {
            runCycle()
        }
        val out = fishAtStage.values

        return out.sum()
    }

    fun String.prepareInput(): List<Int> {
        return this.trimIndent()
            .split(",")
            .map{ it.toInt() }
    }

    val input = getInput(6,2021).prepareInput()
    val testInput = readInput("2021-6-test").prepareInput()

    check(part1(testInput, 80) == 5934.toLong())
    check(part1(testInput, 256) == 26984457539)

    println(part1(input, 80))
    println(part1(input, 256))

}
