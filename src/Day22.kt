import utils.*

fun main() {
    fun part1(input: List<Pair<Boolean, List<Int>>>): Int {
        fun fix(a: Int) = a + 50

        val grid = MutableList(101) {MutableList(101) { MutableList(101) {false} } }
        // Znao sam kakav će drugi dio biti, i dalje sam ovako radio

        for ((b, coords) in input.filter { it1 -> it1.second.all { -50 <= it && it <= 50 } }) {
            val a = coords.map{ fix(it) }
            val (x, y, z) = a.windowed(2, 2)
            if (b) {
                (x.first()..x.last()).forEach { x1->
                    (y.first()..y.last()).forEach { y1 ->
                        (z.first()..z.last()).forEach { z1 ->
                            grid[x1][y1][z1] = true
                        }
                    }
                }
            } else {
                (x.first()..x.last()).forEach { x1->
                    (y.first()..y.last()).forEach { y1 ->
                        (z.first()..z.last()).forEach { z1 ->
                            grid[x1][y1][z1] = false
                        }
                    }
                }
            }
        }
        return grid.flatten().flatten().count { it }
    }

    fun part2(input: List<Pair<Boolean, List<Int>>>): Long {
        var seen = mutableListOf<Pair<Boolean, MutableList<Int>>>()

        for ((b, c) in input) {
            var (x1, x2, y1, y2, z1, z2) = c.take(6) // Morao sam dodati List<T>.component6() u Utils.kt da bi radilo, inače samo do 5 elemenata ide
            x2 += 1; y2 += 1; z2 += 1
            val newSeen = mutableListOf<Pair<Boolean, MutableList<Int>>>()
            for ((b1, c1) in seen) {
                if (!(x1 >= c1[1] || x2 <= c1[0] || y1 >= c1[3] || y2 <= c1[2] || z1 >= c1[5] || z2 <= c1[4])) {
                    if (x1 > c1[0]) {
                        val new = Pair(b1, c1.take(1) + x1 + c1.takeLast(4))
                        c1[0] = x1
                        newSeen += new as Pair<Boolean, MutableList<Int>> // Compiler me ne voli zbog ovoga
                        // Warning:(46, 40) Unchecked cast: Pair<Boolean, List<Int>> to Pair<Boolean, MutableList<Int>>
                    }
                    if (x2 < c1[1]) {
                        val new = Pair(b1, listOf(x2) + c1.takeLast(5) )
                        c1[1] = x2
                        newSeen += new as Pair<Boolean, MutableList<Int>>
                    }
                    if (y1 > c1[2]) {
                        val new = Pair(b1, c1.take(3) + y1 + c1.takeLast(2))
                        c1[2] = y1
                        newSeen += new as Pair<Boolean, MutableList<Int>>
                    }
                    if (y2 < c1[3]) {
                        val new = Pair(b1, c1.take(2) + (y2) + c1.takeLast(3))
                        c1[3] = y2
                        newSeen += new as Pair<Boolean, MutableList<Int>>
                    }
                    if (z1 > c1[4]) {
                        val new = Pair(b1, c1.take(5) + z1)
                        c1[4] = z1
                        newSeen += new as Pair<Boolean, MutableList<Int>>
                    }
                    if (z2 < c1[5]) {
                        val new = Pair(b1, c1.take(4) + (z2) + c1.takeLast(1))
                        c1[5] = z2
                        newSeen += new as Pair<Boolean, MutableList<Int>>
                    }
                } else newSeen.add(Pair(b1, c1))
            }
            newSeen += Pair(b, listOf(x1,x2,y1,y2,z1,z2)) as Pair<Boolean, MutableList<Int>>
            seen = newSeen
        }

        var total = 0L
        for ((b, c) in seen) {
            if (b) {
                total += ( c[1] - c[0] ).toLong() * ( c[3] - c[2] ).toLong() * ( c[5] - c[4] ).toLong()
                //       (  x2  -  x1  )          * (  y2  -  y1  )          * (  z2  -  z1  )
                // Volumen dijela
            }
        }
        return total
    }

    fun String.prepareInput(): List<Pair<Boolean, List<Int>>> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
            .map{ it1 -> Pair(it1.contains("on"), Regex("-*\\d+").findAll(it1).map { it.value.toInt() }.toList()) }
    }

    val input = getInput(22,2021).prepareInput()
    val testInput = readInput("2021-22-test").prepareInput()
    check(part1(testInput) == 474140)
    check(part2(testInput) == 2758514936282235L)
    println(part1(input))
    println(part2(input))
}
