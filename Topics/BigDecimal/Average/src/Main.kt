import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    val a = readLine()!!.toBigDecimal()
    val b = readLine()!!.toBigDecimal()
    val c = readLine()!!.toBigDecimal()

    print(((a + b + c) / BigDecimal(3)).setScale(0, RoundingMode.DOWN))

}