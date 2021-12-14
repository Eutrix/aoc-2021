import utils.*

fun main() {
    fun part1(input: Pair<String, Set<Pair<String, Set<String>>>>, num: Int): Long {
        val l = input.first
        val r = input.second.toMap()
        var popis = l.windowed(2).map { it to 1L }
        for (i in 1..num) {
            var newPopis = popis
                .map { it1 ->
                    r[it1.first]!!
                        .map { it to it1.second }
                }
                .flatten()
            newPopis = newPopis
                .distinctBy { it.first }
                .map { it1 ->
                    it1.first to newPopis
                        .filter { it.first == it1.first }
                        .sumOf { it.second }
                }

            popis = newPopis
        }
        val mapa = mutableMapOf<Char, Long>()
        popis.forEach {
            mapa[it.first.first()] = (mapa[it.first.first()] ?: 0L) + it.second
        }
        return mapa.maxOf { it.value } - mapa.minOf { it.value } + 1
    }

    fun String.prepareInput(): Pair<String, Set<Pair<String, Set<String>>>> {
        val a = this.split("\n\n")
        val str = a.first()
        val rules = a[1].split("\n").filter { it.isNotEmpty() }.map { it1 ->
            val (c, b) = it1.split(" -> ")
            c to setOf(c.first() + b, b + c.last())
        }.toSet()
        return Pair(str, rules)
    }

    val input = getInput(14, 2021).prepareInput()
    val testInput = readInput("2021-14-test").prepareInput()

    check(part1(testInput, 10) == 1588L)
    check(part1(testInput, 40) == 2188189693529)

    println(part1(input, 10))
    println(part1(input, 40))
}
