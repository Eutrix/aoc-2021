import utils.*
// Nije brute ako runna u manje od sekunde
fun main() {
    fun part1(input: Pair<Pair<Int, Int>, Pair<Int, Int>>): Int {
        var xPos: Int
        var yPos: Int
        var xVel: Int
        var yVel: Int
        var largestTotalY = 0
        val solutions = (input.first.first..input.first.second).map { x ->
            (input.second.first..input.second.second).map { Pair(x, it) }
        }.flatten().toSet() // Setovi imaju puno brži lookup nego liste
        // 663k lookupova za test input, 992k za pravi input
        val minY = solutions.minOf { it.second }
        val maxX = solutions.maxOf { it.first }
        for (xV in -20..100) { // Brojevi su random, samo sam ih stavio na -500, 500, pa smanjivao da brze racuna
            for (yV in 0..150) {
                xPos = 0
                yPos = 0
                xVel = xV
                yVel = yV
                var largestY = 0
                while (xPos <= maxX && yPos >= minY) {
                    xPos += xVel
                    yPos += yVel
                    if (xVel > 0) xVel -= 1 else if (xVel < 0) xVel += 1
                    yVel -= 1
                    if (yPos > largestY) largestY = yPos
                    if (Pair(xPos, yPos) in solutions) {
                        if (largestY > largestTotalY) largestTotalY = largestY
                    }


                }
            }
        }
        return largestTotalY
    }

    fun part2(input: Pair<Pair<Int, Int>, Pair<Int, Int>>): Int {
        var xPos: Int
        var yPos: Int
        var xVel: Int
        var yVel: Int
        var total = 0
        val solutions = (input.first.first..input.first.second).map { x ->
            (input.second.first..input.second.second).map { Pair(x, it) }
        }.flatten().toSet()
        // oko 1.84m lookupova za pravi input i 1.23m za test input
        val minY = solutions.minOf { it.second }
        val maxX = solutions.maxOf { it.first }
        for (xV in -20..200) {
            for (yV in -200..200) {
                xPos = 0
                yPos = 0
                xVel = xV
                yVel = yV
                outer@while (xPos <= maxX+1 && yPos >= minY) {
                    xPos += xVel
                    yPos += yVel
                    if (xVel > 0) xVel -= 1 else if (xVel < 0) xVel += 1
                    yVel -= 1
                    if (Pair(xPos, yPos) in solutions) {
                        total += 1
                        break@outer // Imao sam problem s double countanjem nekih stvari, hvala debugger
                    }


                }
            }
        }
        return total
    }

    fun String.prepareInput(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val a = Regex("(-*\\d+)..(-*\\d+)").findAll(this)
        return a.map { it1 ->
            it1.groupValues // [a..b, a, b]
                .subList(1,3) // [a, b]
                .map { it.toInt() } // [a, b] ali brojevi
                .toPair() } // (a, b)
            .toPair() // ((a, b), (c, d))
    }

    val input = getInput(17,2021).prepareInput()
    val testInput = readInput("2021-17-test").prepareInput()

    check(part1(testInput) == 45)
    check(part2(testInput) == 112)
    println(part1(input))
    println(part2(input))

}
