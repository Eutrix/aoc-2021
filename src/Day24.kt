import utils.*

// Postupak u Day24-postupak.txt
// Ne rjesava zadatak, samo provjerava ako su rjesenja dobivena rukom valid
// Ne zna ako je rjesenje najvece/najmanje
fun main() {
    fun doOp(command: String, n1: Int, n2: Int): Int {
        return when (command) {
            "add" -> n1 + n2
            "mul" -> n1 * n2
            "div" -> n1 / n2
            "mod" -> n1 % n2
            "eql" -> if (n1 == n2) 1 else 0
            "inp" -> throw IllegalArgumentException("missed an input smh my head")
            else -> throw IllegalArgumentException("bad command: $command")
        }
    }

    fun part1(i: Long, input: List<String>): Boolean {
        val good = input.map { if (it.split(" ").first() == "inp") "$it 0" else it }.map { it.split(" ") }
        if (i.toString().contains('0')) throw IllegalArgumentException("Nula u inputu")
        val data = mutableMapOf("x" to 0, "y" to 0, "z" to 0, "w" to 0)
        val g = i.toString().toList()
        val puts = g.map { it.digitToInt() }.iterator()
        for ((c, n1, n2) in good) {
            if (c == "inp") {
                data[n1] = puts.next()
            } else {
                data[n1] = doOp(c, data[n1]!!, data.getOrDefault(n2, null) ?: n2.toInt())
            }
        }
        return data["z"] == 0
    }

    fun String.prepareInput(): List<String> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
    }

    val input = getInput(24, 2021).prepareInput()

    // Postupak u Day24-postupak.txt
    println(part1(95299897999897, input))
    println(part1(31111121382151, input))
}
