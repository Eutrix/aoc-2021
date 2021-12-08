import utils.*

fun main() {
    fun part1(): String {
        return "https://regexr.com/6b43p"
    }

    fun part2(input: List<List<List<String>>>): Int {
        /**
         * 2 - 1
         * 3 - 7
         * 4 - 4
         * 7 - 8
         * 2 - len 5 i 2 zaj. elementa s 4
         * 3 - len 5 i 2 zaj. elementa s 1
         * 5 - len 5 i nije 2 ili 3
         * 6 - len 6 i nema sve elemente iz 7
         * 0 - len 6 i nema sve elemente iz 4 i nije 6
         * 9 - len 6 i nije 6 ili 9
         */

        var total = 0
        for (line in input) {
            val inp = line.first().map { it.toCharArray().sorted().joinToString("") }
            val out = line.last().map { it.toCharArray().sorted().joinToString("") }
            val popis = mutableMapOf<Int, String>()
            popis[1] = inp.first { it.length == 2 }
            popis[4] = inp.first { it.length == 4 }
            popis[7] = inp.first { it.length == 3 }
            popis[8] = inp.first { it.length == 7 }

            popis[2] = inp.first { it.length == 5 &&
                                   it.toSet().intersect(popis[4]!!.toSet()).size == 2 }

            popis[3] = inp.first { it.length == 5 &&
                                   it.toSet().intersect(popis[1]!!.toSet()).size == 2 }

            popis[5] = inp.first { it.length == 5 &&
                                   it !in popis.values }

            popis[6] = inp.first { it1 -> it1.length == 6 &&
                                   popis[1]!!.any { it !in it1 } }

            popis[0] = inp.first { it1 -> it1.length == 6 &&
                                   popis[4]!!.any { it !in it1 } &&
                                   it1 !in popis.values }

            popis[9] = inp.first { it.length == 6 && it !in popis.values }
            var number = ""
            out.forEach { it1 -> number += popis.filterValues { it == it1 }.keys.first() }
            total += number.toInt()
        }
        return total
    }

    fun String.prepareInput(): List<List<List<String>>> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
            .map { it1 ->
                it1.split(" | ")
                    .map { it.split(" ") }
            }
    }

    val input = getInput(8,2021).prepareInput()
    println(part1())
    println(part2(input))
}
