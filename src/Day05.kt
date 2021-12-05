import utils.*

fun main() {

    fun verticalCheck(e1: List<Int>, e2: List<Int>): Char {
        return if (e1.first() == e2.first()) {
            'X'
        } else if (e1.last() == e2.last()) {
            'Y'
        } else {
            '0'
        }
    }

    fun part1(input: List<List<List<Int>>>): Int {
        val popis = MutableList(1000) {MutableList(1000) {0} }

        for (line in input) {
            val e1 = line[0]
            val e2 = line[1]

            if (verticalCheck(e1, e2) == 'X') {
                val larger = maxOf(e1.last(), e2.last())
                val smaller = minOf(e1.last(), e2.last())
                (smaller..larger).forEach { popis[e1.first()][it] += 1 }
            }

            else if (verticalCheck(e1, e2) == 'Y') {
                val larger = maxOf(e1.first(), e2.first())
                val smaller = minOf(e1.first(), e2.first())
                (smaller..larger).forEach { popis[it][e1.last()] += 1 }
            }
        }

        return popis.flatten().count { it >= 2 }
    }

    fun part2(input: List<List<List<Int>>>): Int {
        /** Part 1 code **/
        val popis = MutableList(1000) {MutableList(1000) {0} }

        for (line in input) {
            val e1 = line[0]
            val e2 = line[1]

            if (verticalCheck(e1,e2) == 'X') {
                val larger = maxOf(e1.last(), e2.last())
                val smaller = minOf(e1.last(), e2.last())
                (smaller..larger).forEach { popis[e1.first()][it] += 1 }
            }

            else if (verticalCheck(e1,e2) == 'Y') {
                val larger = maxOf(e1.first(), e2.first())
                val smaller = minOf(e1.first(), e2.first())
                (smaller..larger).forEach { popis[it][e1.last()] += 1 }
            }

            /** New code **/
            else if (verticalCheck(e1,e2) == '0') {
                /* The bad code I used to get the star

                val largerX = maxOf(e1.first(), e2.first())
                val largerY = maxOf(e1.last(), e2.last())

                if (largerX == e1.first() && largerY == e1.last()) {
                    var inc = 0
                    (e2.first()..e1.first()).forEach {
                        popis[it][e2.last()+inc] += 1
                        inc += 1
                    }
                }
                else if (largerX == e1.first() && largerY == e2.last()) {
                    var inc = 0
                    (e2.first()..e1.first()).forEach {
                        popis[it][e2.last()-inc] += 1
                        inc += 1
                    }
                }
                else if (largerX == e2.first() && largerY == e1.last()) {
                    var inc = 0
                    (e1.first()..e2.first()).forEach {
                        popis[it][e1.last()-inc] += 1
                        inc += 1
                    }
                }
                else if (largerX == e2.first() && largerY == e2.last()) {
                    var inc = 0
                    (e1.first()..e2.first()).forEach {
                        popis[it][e1.last()+inc] += 1
                        inc += 1
                    }
                }
                 */

                /** Better code I wrote after getting the star **/
                var xrange = (e1.first()..e2.first()).toList()
                if (xrange.isEmpty()) xrange = (e1.first() downTo e2.first()).toList()
                var yrange = (e1.last()..e2.last()).toList()
                if (yrange.isEmpty()) yrange = (e1.last() downTo e2.last()).toList()

                (xrange zip yrange).forEach { (x,y) ->
                    popis[x][y] += 1
                }


            }
        }

        return popis.flatten().count { it >= 2 }
    }

    fun String.prepareInput(): List<List<List<Int>>> {
        return this.split("\n").filter { it.isNotEmpty() }
                      .map { it1 -> it1.split(" -> ")
                         .map { it2 -> it2.split(",")
                            .map { it.toInt() }
                         }
                      }
    }
    val input = getInput(5,2021).prepareInput()
    val testInput = readInput("2021-5-test").prepareInput()

    check(part1(testInput) == 5)
    check(part2(testInput) == 12)
    println(part1(input))
    println(part2(input))
}
