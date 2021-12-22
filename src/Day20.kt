import utils.getInput
import utils.readInput

fun main() {
    fun getVal(n: Pair<Int, Int>, b: List<String>, filterString: List<Boolean>, outsideChar: Char): Boolean {
        var a = ""
        val (x, y) = n

        val neighbors = listOf(
            Pair(x - 1, y - 1), // #../.../...
            Pair(x, y - 1),     // .#./.../...
            Pair(x + 1, y - 1), // ..#/.../...
            Pair(x - 1, y),     // .../#../...
            Pair(x, y),         // .../.#./...
            Pair(x + 1, y),     // .../..#/...
            Pair(x - 1, y + 1), // .../.../#..
            Pair(x, y + 1),     // .../.../.#.
            Pair(x + 1, y + 1)  // .../.../..#
        )

        neighbors.forEach { a += b.getOrNull(it.second)?.getOrNull(it.first) ?: outsideChar }

        val c = a.map { if (it == '#') '1' else '0'}
            .joinToString("")
            .toInt(radix = 2)

        return filterString[c]

    }

    fun part1(alg: List<Boolean>, b: List<String>, c: Char): List<String> {
        val outB = mutableListOf<String>()
        for (y in -1..b.size + 1) {
            var line = ""
            for (x in -1..b.first().length + 1) {
                val g = getVal(Pair(x, y), b, alg, c)
                line += if (g) '#' else '.'
            }
            outB.add(line)
        }
        return outB
    }

    fun String.prepareInput(): Pair<List<Boolean>, List<String>> {
        val a = this.split("\n\n").filter { it.isNotEmpty() }
        return Pair(a.first().trim().map { it == '#' }, a.last().split("\n").filter { it.isNotEmpty() })
    }

    val input = getInput(20, 2021).prepareInput()
    val testInput = readInput("2021-20-test").prepareInput()

    var testGrid = testInput.second
    val testAlg = testInput.first
    var testC = '.'
    for (i in 1..50) {
        testGrid = part1(testAlg, testGrid, testC)
        testC = if (testC == '#') {
            if (testAlg.last()) '#' else '.'
        } else if (testAlg.first()) '#' else '.'
        if (i == 2) check(testGrid.map { it1 -> it1.count { it == '#' } }.sumOf { it } == 35)
    }
    check(testGrid.map { it1 -> it1.count { it == '#' } }.sumOf { it } == 3351)

    var grid = input.second
    var c = '.'
    val alg = input.first
    for (i in 1..50) {
        grid = part1(alg, grid, c)
        c = if (c == '#') { // Ako je c '#' ce evaluation biti 0b111111111, pa uzimam zadnji element algoritma
            if (alg.last()) '#' else '.'
        } else if (alg.first()) '#' else '.' // Ako je c '.' ce evaluation biti 0b000000000, pa uzimam prvi element algoritma
        if (i == 2) println(grid.map { it1 -> it1.count { it == '#' } }.sumOf { it }) // part 1
    }
    println(grid.map { it1 -> it1.count { it == '#' } }.sumOf { it })
}
