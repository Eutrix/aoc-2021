import utils.*

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        val points = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )
        val rightToLeft = mapOf(
            ')' to '(',
            ']' to '[',
            '}' to '{',
            '>' to '<',
        )

        val leftToRight = mutableMapOf<Char, Char>().also {
            rightToLeft.forEach { (k, v) -> it[v] = k }
        }.toMap()

        for (line in input) {
            val opened = mutableListOf<Char>()
            outer@for (char in line) {
                if (char in "([{<") opened.add(char)
                else if (char in ")]}>") {
                    if (rightToLeft[char] == opened.last()) opened.removeLast()
                    else {
                        val expected = leftToRight[opened.last()]
                        println("Expected ${expected}, found $char instead. In line $line")
                        total += points[char]!!
                        break@outer
                    }
                }
            }
        }
        return total
    }

    fun part2(input: List<String>): Long {
        val total = mutableListOf<Long>()

        val points2 = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )
        val rightToLeft = mapOf(
            ')' to '(',
            ']' to '[',
            '}' to '{',
            '>' to '<',
        )
        val leftToRight = mutableMapOf<Char, Char>().also {
            rightToLeft.forEach { (k, v) -> it[v] = k }
        }.toMap() // StackOverflow code za reversat map

        for (line in input) {
            var goodLine = true
            val opened = mutableListOf<Char>()

            outer@for (char in line) {
                if (char in "([{<") opened.add(char)

                else if (char in ")]}>") {
                    if (rightToLeft[char] == opened.last()) opened.removeLast()
                    else {
                        goodLine = false
                        break@outer
                    }
                }
            }

            if (goodLine) {
                var score = 0L
                for (char in opened.reversed()) {
                    score *= 5
                    score += points2[leftToRight[char]]!!
                }
                total.add(score)
            }
        }
        return total.sorted()[total.size/2]
    }

    fun String.prepareInput(): List<String> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
    }

    val input = getInput(10,2021).prepareInput()
    val testInput = readInput("2021-10-test").prepareInput()

    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    println(part1(input))
    println(part2(input))
}
