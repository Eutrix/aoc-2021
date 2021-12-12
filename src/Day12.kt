import utils.*

fun bfs(cn: MutableMap<String, MutableSet<String>>, c: String, v: MutableSet<String>, d: Boolean): Int {
    if (c == "end") return 1
    var total = 0

    for (n in cn[c]!!) {
        var newD = d
        if (c == c.lowercase() && c in v) {
            if (d || c in setOf("start", "end")) continue
            else newD = true
        }
        total += bfs(cn, n, (v + setOf(c)).toMutableSet(), newD)
    }
    return total
}

fun main() {
    fun part1(input: MutableMap<String, MutableSet<String>>): Int {
        val v = mutableSetOf<String>()
        return bfs(input, "start", v, true)
    }

    fun part2(input: MutableMap<String, MutableSet<String>>): Int {
        val v = mutableSetOf<String>()
        return bfs(input, "start", v, false)
    }

    fun String.prepareInput(): MutableMap<String, MutableSet<String>> {
        val paths: MutableMap<String, MutableSet<String>> = mutableMapOf()
        val thing =  this.split("\n")
            .filter { it.isNotEmpty() }
            .map{ it.split("-")
                .zipWithNext()
            }
            .flatten()
        for (t in thing) {
            paths[t.first] = (paths.getOrDefault(t.first, mutableSetOf(t.second)) + setOf(t.second)).toMutableSet()
            paths[t.second] = (paths.getOrDefault(t.second, mutableSetOf(t.first)) + setOf(t.first)).toMutableSet()
        }
        return paths
    }

    val input = getInput(12,2021).prepareInput()
    val testInput = readInput("2021-12-test").prepareInput()

    check(part1(testInput) == 19)
    check(part2(testInput) == 103)

    println(part1(input))
    println(part2(input))
}
