import utils.*

fun main() {
    fun part1(input: List<List<String>>): Int {
        var i = input.toMutableList().map { it.toMutableList() }
        val maxY = i.size
        val maxX = i.first().size
        fun infront(y: Int, x: Int, g: List<List<String>>): Boolean {
            // Returna true ako je clear, false ako je zauzeto
            return when (i[y][x]) {
                ">" -> g[y][(x + 1) % maxX] == "."
                "v" -> g[(y + 1) % maxY][x] == "."
                else -> throw IllegalArgumentException("no")
            }
        }
        var ct = 0
        outer@ while (true) {
            var mv = false
            val newI = i.map { it.toMutableList() }.toMutableList()
            for (y in i.indices) {
                for (x in i.first().indices) {
                    if (i[y][x] == ">" && infront(y, x, i)) {
                        newI[y][x] = "."
                        newI[y][(x + 1) % maxX] = ">"
                        mv = true
                    }
                }
            }
            i = newI.map { it.toMutableList() }.toMutableList()
            for (y in i.indices) {
                for (x in i.first().indices) {
                    if (i[y][x] == "v" && infront(y, x, i)) {
                        newI[y][x] = "."
                        newI[(y + 1) % maxY][x] = "v"
                        mv = true
                    }
                }
            }
            i = newI
            ct += 1
            if (!mv) break@outer
        }
        return ct
    }

    fun part2(input: List<List<String>>): String {
        return "We did it Patrick! We saved Christmas."
    }

    fun String.prepareInput(): List<List<String>> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
            .map { it1 ->
                it1.split("")
                .filter { it.isNotEmpty() }
            }
    }

    val input = getInput(25, 2021).prepareInput()
    val testInput = readInput("2021-25-test").prepareInput()

    check(part1(testInput) == 58)
    check(part2(testInput) == "We did it Patrick! We saved Christmas.")
    println(part1(input))
    // Rj: 456
    // 😱😱😱 HOLY FUCKING SHIT‼️‼️‼️‼️ IS THAT A MOTHERFUCKING SQUID GAME REFERENCE??????!!!!!!!!!!11!1!1!1!1!1!1! 😱😱😱😱😱😱😱
    // SQUID GAME IS THE BEST FUCKING SHOW 🔥🔥🔥🔥💯💯💯💯
    println(part2(input))
}
