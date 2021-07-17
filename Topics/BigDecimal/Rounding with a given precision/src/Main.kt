import java.math.RoundingMode

fun main() {             
    val number = readLine()!!.toBigDecimal()
    val scale = readLine()!!.toInt()

    println(number.setScale(scale, RoundingMode.HALF_DOWN))
}