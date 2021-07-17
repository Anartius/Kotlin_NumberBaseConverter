import java.math.BigInteger

fun main() {
    val n = readLine()!!.toBigInteger()
    val exaByte = BigInteger("9223372036854775808")
    print(n.multiply(exaByte))
}