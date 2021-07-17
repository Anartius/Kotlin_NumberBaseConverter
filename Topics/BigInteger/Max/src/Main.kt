import java.math.BigInteger

fun main() {
    val a = readLine()!!.toBigInteger()
    val b = readLine()!!.toBigInteger()
    val two = BigInteger.TWO
    print((a + b + (a - b).abs()) / two)
}