import utils.*

fun main() {
    fun part1(numbers: List<Int>, boards: List<List<List<Int>>>): Int {
        val old = mutableSetOf<Int>()
        var winner: List<List<Int>> = emptyList()
        var winningNum = 0

        breakAll@ for (drawnNum in numbers) {
            old.add(drawnNum)
            for (board in boards) {
                for (row in board) if (old.containsAll(row)) {
                    winner = board
                    winningNum = old.last()
                    break@breakAll
                }
                for (col in board.indices) {
                    val colNums = mutableSetOf<Int>()
                    board.forEach{colNums.add(it[col])}

                    if (old.containsAll(colNums)) {
                        winner = board
                        winningNum = old.last()
                        break@breakAll
                    }
                }
            }
        }

        var unmarkedTotal = 0

        winner.flatten()
            .forEach { unmarkedTotal += if (!old.contains(it)) it else 0 }

        return unmarkedTotal * winningNum
    }

    fun part2(numbers: List<Int>, boards: List<List<List<Int>>>): Int {
        val old = mutableSetOf<Int>()
        var lastWinner = emptyList<List<Int>>()
        var lastWinnerNum = 0
        val allWinners = mutableSetOf<Int>()
        breakAll@ for (drawnNumber in numbers) {
            old.add(drawnNumber)

            for ((index, board) in boards.withIndex()) {
                for (row in board) if (old.containsAll(row)) {
                    if (allWinners.size == boards.size - 1 && index !in allWinners) {
                        lastWinner = board
                        lastWinnerNum = drawnNumber
                        break@breakAll
                    }
                    else allWinners.add(index)
                }
                for (col in board.indices) {
                    val colNums = mutableListOf<Int>()
                    board.forEach { colNums.add(it[col]) }

                    if (old.containsAll(colNums)) {
                        if (allWinners.size == boards.size - 1 && index !in allWinners) {
                            lastWinner = board
                            lastWinnerNum = drawnNumber
                            break@breakAll
                        }
                        else allWinners.add(index)
                    }
                }
            }
        }
        var unmarkedTotal = 0

        lastWinner.flatten()
            .forEach { unmarkedTotal += if (!old.contains(it)) it else 0 }


        return unmarkedTotal * lastWinnerNum
    }

    val input = getInput(4,2021)

    val numbers = input[0]
        .split(",")
        .map {it.toInt()}

    val boards = input.subList(2, input.size)
        .windowed(5, 6)
        .map {
                board -> board.map {
                row -> row.trimIndent()
                    .split(Regex("\\s+"))
                    .map {it.toInt()}
            }
        }

    println(part1(numbers, boards))
    println(part2(numbers, boards))
}
