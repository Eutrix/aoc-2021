package utils

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/input", "$name.txt").readText()

fun getSession() = File(".session").readText().trim()

fun getInput(day: Int, year: Int): String {
    if (File("src/input", "$year-$day.txt").exists()) {
        println("Using cached data at src/input/$year-$day.txt")
        return File("src/input", "$year-$day.txt").readText()
    }
    else {
        println("Downloading and saving input data")
        val session = getSession()
        /** HttpURLConnection is for nerds. Real gamers use curl. **/

        val command = "curl -b session=$session https://adventofcode.com/$year/day/$day/input"
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(
            InputStreamReader(process.inputStream)
        )

        val out = reader.readText()
        val file = File("src/input", "$year-$day.txt")
        file.appendText(out)
        return out
    }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
