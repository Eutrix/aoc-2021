import utils.*

fun main() {
    fun flash(x: Int, y: Int, inp: List<MutableList<Int>>, seen: MutableSet<Pair<Int, Int>>) {
        val maxX = inp.size
        val maxY = inp[0].size
        val neighbors = setOf(
            Pair(x + 1, y + 1),
            Pair(x + 1, y),
            Pair(x + 1, y - 1),
            Pair(x, y + 1),
            Pair(x, y - 1),
            Pair(x - 1, y + 1),
            Pair(x - 1, y),
            Pair(x - 1, y - 1)
        ).filter {
            -1 < it.first &&
            it.first < maxX &&
            -1 < it.second &&
            it.second < maxY
        }
        seen.add(Pair(x, y))

        for (n in neighbors) {
            if (n !in seen) {
                inp[n.first][n.second] += 1
                if (inp[n.first][n.second] > 9) {
                    flash(n.first, n.second, inp, seen)
                }
            }
        }
    }

    fun part1(input: List<List<Int>>): Int {
        var cnt = 0
        val inp = input.map { it.toMutableList() }
        val maxX = input.size
        val maxY = input[0].size

        for (step in 1..100) {
            inp.forEach { it1 -> it1.replaceAll { it + 1 } }

            val seen = mutableSetOf<Pair<Int, Int>>()
            for (x in 0 until maxX) {
                for (y in 0 until maxY) {
                    if (inp[x][y] > 9 && Pair(x, y) !in seen) {
                        flash(x, y, inp, seen)
                    }
                }
            }
            seen.forEach { inp[it.first][it.second] = 0 }
            cnt += seen.size
        }

        return cnt
    }

    fun part2(input: List<List<Int>>): Int {
        var cnt = 0
        val maxX = input.size
        val maxY = input[0].size
        val inp = input.map { it.toMutableList() }

        while (true) {
            cnt += 1
            inp.forEach { it1 -> it1.replaceAll { it + 1 } }

            val seen = mutableSetOf<Pair<Int, Int>>()
            for (x in 0 until maxX) {
                for (y in 0 until maxY) {
                    if (inp[x][y] > 9 && Pair(x, y) !in seen) {
                        flash(x, y, inp, seen)
                    }
                }
            }
            seen.forEach { inp[it.first][it.second] = 0 }
            if (seen.size == inp.flatten().size) return cnt
        }
    }

    fun String.prepareInput(): List<List<Int>> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
            .map { it1 ->
                it1.split("")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
            }
    }

    val input = getInput(11, 2021).prepareInput()
    val testInput = readInput("2021-11-test").prepareInput()

    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    println(part1(input))
    println(part2(input))
}

