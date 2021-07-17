import java.math.BigInteger

fun main() {
    val (a, b) = Array(2) { BigInteger(readLine()!!) }
    val result = a.divideAndRemainder(b)
    print("$a = $b*${result[0]} + ${result[1]}")
}