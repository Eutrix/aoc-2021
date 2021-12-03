import utils.*

fun main() {
    fun mostCommon(a: List<Char>): Char {
        
        if (a.count{ it == '1' } >= a.count{ it == '0' }) {
            return '1'
        }
        return '0'
    }
    
    fun leastCommon(b: List<Char>): Char {
        
        if (b.count{ it == '0' } > b.count{ it == '1' }) {
            return '1'
        }
        return '0'
    }

    fun part1(input: List<String>): Int {
        
        val positions = (0..11).map {
                i -> input.map { it[i] }
        }

        val gamma = positions.map { mostCommon(it) }
            .joinToString("")
            .toInt(2)
        val epsilon = positions.map { leastCommon(it) }
            .joinToString("")
            .toInt(2)

        return gamma * epsilon
    }
    
    fun part2(input: List<String>): Int {
        
        fun findMostCommon(a: List<String>): Int {
            val popis = a.toMutableList()
            var i = 0
            while (popis.size > 1) {
                val next = mostCommon(popis.map { it[i] })
                popis.retainAll { it[i] == next }
                i++
            }
            return popis.single().toInt(2)
        }

        fun findLeastCommon(b: List<String>): Int {
            val popis = b.toMutableList()
            var i = 0
            while (popis.size > 1) {
                val next = leastCommon(popis.map { it[i] })
                popis.retainAll { it[i] == next }
                i++
            }
            return popis.first().toInt(2)
        }

        val o2 = findMostCommon(input)
        val co2 = findLeastCommon(input)

        return o2 * co2
    }

    val input = getInput(3,2021)
    println(part1(input))
    println(part2(input))
}
