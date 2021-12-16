import java.util.PriorityQueue // Dugo mi je trebalo za skuzit kako ovo radi
import utils.*

fun main() {
    fun MutableMap<Pair<Int, Int>, Int>.getNeigh(it: Pair<Int, Int>): Set<Pair<Pair<Int, Int>, Int>> {
        return setOf(
            Pair(it.first + 1, it.second),
            Pair(it.first - 1, it.second),
            Pair(it.first, it.second + 1),
            Pair(it.first, it.second -1)
        )
            .filter { this.contains(it) }
            .map { Pair(it, this[it] ?: 99999999) }
            .toSet()
    }

    fun dijkstra(dest: Pair<Int, Int>, a: MutableMap<Pair<Int, Int>, Int>): Int {
        // džikstra

        val start = Pair(0, 0)
        val risk = mutableMapOf(Pair(start, 0))
        val q = PriorityQueue<Pair<Pair<Int, Int>, Int>>(compareBy{ it.second })
        // q.remove() uzima prvo one s najmanjim rizikom

        q.add(Pair(start, 0))

        while (q.isNotEmpty()) {
            val neigh = q.remove()
            for (adj in a.getNeigh(neigh.first)) {
                val nr = adj.second + neigh.second
                if (risk.getOrDefault(adj.first, 9999999) > nr) {
                    risk[adj.first] = nr
                    q.add(Pair(adj.first, nr))
                }
            }
        }

        return risk[dest]!!
    }

    fun part1(input: Pair<MutableMap<Pair<Int, Int>, Int>, Int>): Int {
        val a = input.first
        val maxX = a.keys.maxOf { it.first }
        val maxY = a.keys.maxOf { it.second }

        return dijkstra(Pair(maxX, maxY), a)
    }


    fun part2(input: Pair<MutableMap<Pair<Int, Int>, Int>, Int>): Int {
        val a = input.first
        val n = input.second
        for (y in 0 until n * 5) {
            for (x in 0 until n * 5) {
                if (!a.contains(Pair(x, y))) {
                    val x2 = x / n
                    val x3 = x % n
                    val y2 = y / n
                    val y3 = y % n
                    var cur = a[Pair(x3, y3)]!! + x2 + y2
                    val oldCur = cur
                    cur %= 9
                    if (cur == 0) {
                        cur = oldCur
                    }

                    a[Pair(x, y)] = cur
                }
            }
        }

        val maxX = a.keys.maxOf { it.first }
        val maxY = a.keys.maxOf { it.second }

        return dijkstra(Pair(maxX, maxY), a)

    }

    fun String.prepareInput(): Pair<MutableMap<Pair<Int, Int>, Int>, Int> {
        val a = this.split("\n").filter { it.isNotEmpty() }
        return Pair(a.indices
            .map { y ->
                a[0].indices.map { Pair(Pair(it, y), a[y][it].toString().toInt()) }
            }
            .flatten()
            .toMap()
            .toMutableMap(), a[0].length)
    }

    val input = getInput(15, 2021).prepareInput()
    val testInput = readInput("2021-15-test").prepareInput()

    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    println(part1(input))
    println(part2(input))

}