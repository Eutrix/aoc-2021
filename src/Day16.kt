import utils.getInput
import utils.readInput

var vTotal = 0
fun main() {
    fun resi(valP: Int, data: String): Pair<Int, Long> {
        var p = valP
        var literal = 0L

        val version = data.substring(p until p + 3).toInt(2)
        vTotal += version
        p += 3

        val type = data.substring(p until p + 3).toInt(2)
        p += 3

        if (type == 4) {
            // Literal
            var strBits = ""
            while (data[p] == '1') {
                p += 1
                strBits += data.substring(p until p + 4)
                p += 4
            }
            p += 1
            strBits += data.substring(p until p + 4)
            p += 4
            literal += strBits.toLong(2)
        } else {
            // Operator
            val lTypeId = data[p]
            val values = mutableListOf<Long>()
            p += 1
            if (lTypeId == '0') {
                // The IntelliJ IDEA debugger was a gift from God himself to all programmers
                val lenTotal = data.substring(p until p + 15).toInt(2)
                p += 15
                val origP = p
                while (p < origP + lenTotal) {
                    val h = resi(p, data) // We do a little recursioning
                    p = h.first
                    values.add(h.second)
                }
            } else {
                // Broj podpaketa
                val a = data.substring(p until p + 11).toInt(2)
                p += 11
                repeat(a) {
                    val h = resi(p, data)
                    p = h.first
                    values.add(h.second)
                }

            }

            when (type) {
                0 -> literal = values.sum()
                1 -> {
                    literal = 1
                    values.forEach { literal *= it }
                }
                2 -> literal = values.minOf { it }
                3 -> literal = values.maxOf { it }
                5 -> literal = if (values.first() > values.last()) 1 else 0
                6 -> literal = if (values.first() < values.last()) 1 else 0
                7 -> literal = if (values.first() == values.last()) 1 else 0
            }
        }
        return Pair(p, literal)
    }

    fun String.prepareInput(): String {
        return this.trim().map {
            it.digitToInt(16)
                .toString(2)
                .padStart(4, '0')
        }
            .joinToString("")
    }

    val input = getInput(16, 2021).prepareInput()
    val testInput = readInput("2021-16-test").split("\n\n").map { it1 -> it1.split("\n").map { it.prepareInput() } }

    val p1Tests = listOf(16, 12, 23, 31)
    testInput[0].forEachIndexed { i, s ->
        vTotal = 0
        resi(vTotal, s)
        check(vTotal == p1Tests[i])
    }

    val p2Tests = listOf(3, 54, 7, 9, 1, 0, 0, 1).map { it.toLong() }
    testInput[1].forEachIndexed { i, s ->
        vTotal = 0
        val res = resi(vTotal, s).second
        check(res == p2Tests[i])
    }

    vTotal = 0
    val res = resi(vTotal, input).second
    println(vTotal) // Part 1
    println(res) // Part 2
}
