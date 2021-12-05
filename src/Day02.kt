import utils.*

fun main() {
    fun part1(input: List<List<String>>): Int {
        var depth = 0
        var xpos = 0

        for (thing in input) {
            val dir = thing.first()
            val amount = thing.last().toInt()

            depth += when(dir) {
                "up" -> -amount
                "down" -> amount
                else -> 0
            }

            if (dir == "forward") {
                xpos += amount
            }
        }
        return xpos * depth
    }

    fun part2(input: List<List<String>>): Int {
        var depth = 0
        var xpos = 0
        var aim = 0

        for (thing in input) {
            val dir = thing.first()
            val amount = thing.last().toInt()

            if (dir == "forward") {
                depth += (aim*amount)
                xpos += amount
            }
            else if (dir == "up") {
                aim -= amount
            }
            else if (dir == "down") {
                aim += amount
            }


        }
        return xpos * depth
    }

    fun String.prepareInput(): List<List<String>> {
        return this.split("\n").filter { it.isNotEmpty() }
            .map{ it.split(" ") }
    }

    val input = getInput(2,2021).prepareInput()
    println(part1(input))
    println(part2(input))


}

