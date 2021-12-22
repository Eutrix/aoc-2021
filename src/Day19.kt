import utils.*
import kotlin.math.abs

fun main() {
    data class Point(val x: Int, val y: Int, val z: Int) {
        operator fun plus(b: Point) = Point(
            x + b.x,
            y + b.y,
            z + b.z
        )

        operator fun minus(b: Point) = Point(
            x - b.x,
            y - b.y,
            z - b.z
        )

        fun rotated(): Set<Point> {
            return setOf(
                Point(x, y, z), Point(x, -y, -z), Point(-x, -y, z), Point(-x, y, -z),
                Point(x, z, -y), Point(x, -z, y), Point(-x, z, y), Point(-x, -z, -y),
                Point(y, z, x), Point(y, -z, -x), Point(-y, -z, x), Point(-y, z, -x),
                Point(y, x, -z), Point(y, -x, z), Point(-y, x, z), Point(-y, -x, -z),
                Point(z, x, y), Point(z, -x, -y), Point(-z, -x, y), Point(-z, x, -y),
                Point(z, y, -x), Point(z, -y, x), Point(-z, y, x), Point(-z, -y, -x)
            // Bez hinta vjv nikad ne bih rijesio ovo: https://www.euclideanspace.com/maths/algebra/matrix/transforms/examples/index.htm
            )
        }
    }

    fun dist(a: Point, b: Point): Int {
        return abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)
    }

    fun List<Set<Point>>.flip(): List<Set<Point>> {
        return if (this.all { it.isNotEmpty() }) listOf(this.map { it.first() }.toSet()) + this.map { it.drop(1).toSet() }.flip() else listOf()
        // Rekurzija ide brrr
    }

    fun Set<Point>.intersectOrNull(b: Set<Point>): Pair<Set<Point>, Point>? {
        return b.map { it.rotated() }
            .flip()
            .firstNotNullOfOrNull { it2 ->
                this.firstNotNullOfOrNull { it1 ->
                    it2.firstNotNullOfOrNull { it3 ->
                        val c = it2.map { it1 - it3 + it }.toSet()
                        if (c.intersect(this).size >= 12) Pair(c, it1 - it3) else null
                    }
                }
            }
    }

    fun bothParts(input: List<Set<Point>>): Pair<Int, Int> {
        val b = input.first().toMutableSet() // Beaconi
        val s = mutableSetOf(Point(0, 0, 0)) // Scanneri
        val remaining = ArrayDeque(input.drop(1))
        while (remaining.isNotEmpty()) {
            val a = remaining.removeFirst()
            val b1 = b.intersectOrNull(a)
            if (b1 != null) {
                b.addAll(b1.first)
                s.add(b1.second)
            } else remaining.add(a)
        }
        return Pair(b.size, s.run { this.combinations(2).maxOf { dist(it.first(), it.last()) } })
    }

    fun String.prepareInput(): List<Set<Point>> {
        return this.split(Regex("--- scanner (\\d+) ---"))
            .filter { it.isNotEmpty() }
            .map { it2 ->
                it2.split("\n")
                    .filter { it.isNotEmpty() }
                    .map { it1 ->
                        it1.split(",")
                            .let { Point(
                                it[0].toInt(),
                                it[1].toInt(),
                                it[2].toInt()
                                )
                            }
                    }
                    .toSet()
            }
    }

    val input = getInput(19, 2021).prepareInput()
    val testInput = readInput("2021-19-test").prepareInput()


    check(bothParts(testInput) == Pair(79, 3621))
    // Radi, samo je užasno sporo
    bothParts(input).also { println("${it.first}\n${it.second}") }

}