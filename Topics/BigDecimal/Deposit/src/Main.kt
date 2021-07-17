import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    val amount = readLine()!!.toBigDecimal()
    val rate = readLine()!!.toBigDecimal().setScale(4, RoundingMode.UNNECESSARY)
    val years = readLine()!!.toInt()
    println("Amount of money in the account: " +
        (amount * (BigDecimal.ONE + rate / "100".toBigDecimal()).pow(years))
        .setScale(2, RoundingMode.CEILING))
}