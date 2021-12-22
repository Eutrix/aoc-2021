import utils.*

fun main() {
    val pairReg = Regex("(\\[\\d+,\\d+\\])")
    val numReg = Regex("(\\d+)")

    fun String.pairToNums(): Pair<Int, Int> {
        val numbers = this.substring(1, this.length - 1).split(',')
        return Pair(numbers[0].toInt(), numbers[1].toInt())
    }

    fun explode(num: String): Pair<String, Boolean> {
        fun lExplode(lNum: Int, l: String): String {
            val g = numReg.findAll(l).lastOrNull() ?: return l
            return "${l.take(g.range.first)}${lNum + g.value.toInt()}${l.takeLast(l.length - g.range.last - 1)}"
        }
        fun rExplode(rNum: Int, r: String): String {
            val h = numReg.findAll(r).firstOrNull() ?: return r
            return "${r.take(h.range.first)}${rNum + h.value.toInt()}${r.takeLast(r.length - h.range.last - 1)}"
        }

        val m = pairReg.findAll(num).firstOrNull {
                it2 ->
            num.take(it2.range.first)
                .let { it1 ->
                    it1.count { it == '[' } - it1.count { it == ']' }
                } == 4 } ?: return Pair(num, false)
        val (lNum, rNum) = m.value.pairToNums()
        var l = num.take(m.range.first)
        var r = num.takeLast(num.length - m.range.last - 1)
        l = lExplode(lNum, l)
        r = rExplode(rNum, r)
        return Pair(("${l}0${r}"), true)
    }


    fun split(num: String): Pair<String, Boolean> {
        val m = numReg.findAll(num).firstOrNull { it.value.toInt() >= 10 } ?: return Pair(num, false)
        val l = m.value.toInt() / 2
        val r = if (m.value.toInt() % 2 == 0) l else l + 1
        val lP = num.take(m.range.first)
        val rP = num.takeLast(num.length - m.range.last - 1)
        return Pair(("$lP[$l,$r]$rP"), true)
    }

    fun mag(num: String): Int {
        val numList = pairReg.findAll(num)
        if (!numList.any { true }) return num.toInt()

        numList.forEach { it1 ->
            return mag("${num.take(it1.range.first)}${it1.value.pairToNums().let { it.first*3 + it.second*2 }}${num.takeLast(num.length - it1.range.last - 1)}")
        }
        throw Error("no bueno")
    }

    fun reduce(num: String): String {
        var res = num
        while (true) {
            var again: Boolean
            explode(res).let {
                res = it.first
                again = it.second
            }
            if (again) continue

            split(res).let {
                res = it.first
                if (!it.second) return res
            }
        }
    }

    fun part1(i: List<String>): Int {
        var num = i.first()
        i.drop(1).forEach { num = reduce("[$num,$it]") }
        return mag(num)
    }

    fun part2(i: List<String>): Int {
        return i
            .combinations(2)
            .map { listOf(
                mag(reduce("[${it[1]},${it[0]}]")),
                mag(reduce("[${it[0]},${it[1]}]"))
            )
            }
            .flatten().maxOf { it }
    }

    fun String.prepareInput(): List<String> {
        return this.split("\n")
            .filter { it.isNotEmpty() }
    }

    val input = getInput(18,2021).prepareInput()
    val testInput = readInput("2021-18-test").prepareInput()
    check(part1(testInput) == 4140)
    check(part2(testInput) == 3993)
    println(part1(input))
    println(part2(input))


}