import utils.*

fun main() {
    data class Klasa(val pts: List<Pair<Int, Int>>, val folds: List<Pair<Boolean, Int>>) {
        fun doTheThing(): Sequence<Klasa> {
            val a = this
            return generateSequence(a) { (pts, folds) ->
                if (folds.isNotEmpty()) {
                    folds.first().let { (d, line) ->
                        Klasa(pts.map {
                            var (x, y) = it
                            if (d) { // True za x fold, False za y fold
                                x = if (x > line) 2 * line - x else x
                            } else {
                                y = if (y > line) 2 * line - y else y
                            }
                            Pair(x, y) }
                            , folds.drop(1))
                    }
                } else null // zavrsi sequence kad prođe kroz sve foldove
            }
        }
    }

    fun part1(input: Klasa): Int {
        return input.doTheThing().toList()[1].pts.distinct().size
    }

    fun part2(input: Klasa): String {
        val p = input.doTheThing().toList().last().pts
        val x = p.maxOf { it.first }
        val y = p.maxOf { it.second }

        return (0..y).joinToString("\n") { y ->
            (0..x).map {
                if (p.contains(Pair(it, y))) '#' else ' '
            }.joinToString("")
        }
    }

    fun String.prepareInput(): Klasa {
        val all = this.split("\n\n")
        return Klasa(
            all[0]
                .split("\n")
                .filter { it.isNotEmpty() }
                .map { it1 ->
                    it1.split(",").map { it.toInt() }.zipWithNext()
                }.flatten(),

            all[1]
                .split("\n")
                .filter { it.isNotEmpty() }
                .map { Pair(it.contains('x'), it.split("=").last().toInt()) }
        )
    }

    val input = getInput(13, 2021).prepareInput()
    val testInput = readInput("2021-13-test").prepareInput()

    check(part1(testInput) == 17)
    check(part2(testInput) ==
"""
#####
#   #
#   #
#   #
#####
""".trim())
    println(part1(input)) // 842
    println(part2(input)) // BFKRCJZU
}
