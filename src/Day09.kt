import utils.*

fun main() {
    fun List<List<Int>>.isLowestPoint(x: Int, y: Int): Boolean {
        val input = this
        val neighbors = setOf(
            Pair(x + 1, y),
            Pair(x - 1, y),
            Pair(x, y - 1),
            Pair(x, y + 1)
        ).filter {
            -1 < it.first &&
            it.first < input.size &&
            -1 < it.second &&
            it.second < input[0].size
        }

        var good = true

        neighbors.forEach { if (input[x][y] >= input[it.first][it.second]) good = false }
        return good
    }

    fun part1(input: List<List<Int>>): Int {
        val a = mutableListOf<Int>()

        for ((x, line) in input.withIndex()) {
            for ((y, num) in line.withIndex()) {
                if (input.isLowestPoint(x, y)) {
                    a.add(num)
                }
            }
        }
        return a.sum() + a.size
    }

    fun part2(input: List<List<Int>>): Int {

        fun searchStuff(x: Int, y: Int, visited: MutableSet<Pair<Int, Int>>) {
            val neighbors = setOf(
                Pair(x + 1, y),
                Pair(x - 1, y),
                Pair(x, y - 1),
                Pair(x, y + 1)
            ).filter {
                -1 < it.first &&
                it.first < input.size &&
                -1 < it.second &&
                it.second < input[0].size
            }

            for (n in neighbors) {
                if (Pair(n.first, n.second) !in visited && input[n.first][n.second] != 9) {
                    visited.add(Pair(n.first, n.second))
                    searchStuff(n.first, n.second, visited)
                }
            }
        }

        val visited = mutableSetOf<Pair<Int, Int>>()
        val basins = mutableListOf<Int>()

        for (x in input.indices) {
            for (y in 0 until input[0].size) {
                val old = visited.size
                if (input[x][y] != 9 && Pair(x, y) !in visited) {
                    visited.add(Pair(x, y))
                    searchStuff(x, y, visited)
                    basins.add(visited.size - old)
                }
            }
        }

        basins.sortDescending()
        return basins[0] * basins[1] * basins[2]
    }

    fun String.prepareInput(): List<List<Int>> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
            .map { it1 ->
                it1.toList()
                    .map { it.toString().toInt() } // Charovi ne vole bit prebacivani u intove
            }
    }

    val input = getInput(9, 2021).prepareInput()
    val testInput = readInput("2021-9-test").prepareInput()

    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    println(part1(input))
    println(part2(input))
}
