package converter
import kotlin.math.pow
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

fun main() {
    var topLevelInput: String
    var downLevelInput: String
    var base: MutableList<Int>
    while (true) {
        print("Enter two numbers in format: {source base} {target base} " +
                "(To quit type /exit) ")
        topLevelInput = readLine()!!

        when (topLevelInput) {
            "/exit" -> return
            else -> {
                base = topLevelInput.split(" ").map {
                    it.toInt()
                }.toMutableList()

                while (true) {
                    // User input
                    print("Enter number in base ${base[0]} to convert to base " +
                            "${base[1]} (To go back type /back) ")
                    downLevelInput = readLine()!!

                    // Go to top level menu
                    if (downLevelInput == "/back") {
                        println("\n")
                        break
                    }

                    // Conversion
                    println("Conversion result: " +
                            when {
                                base[0] == base[1] || downLevelInput == "0" -> {
                                    downLevelInput
                                }
                                base[0] == 10 -> {
                                    decToAny(base[1], downLevelInput)
                                }
                                base[1] == 10 -> {
                                    anyToDec(base[0] , downLevelInput)
                                }
                                else -> {
                                    decToAny(base[1], (anyToDec(base[0], downLevelInput)))
                                }
                            } + "\n")
                }
            }
        }
    }
}

// Conversion from any base to base 10
fun anyToDec(sourceBase: Int, number: String) : String {
    val charDigit = ('A'..'Z').map { it.toString() }.toList()
    val n = if (number.contains('.')) {
        number.uppercase().split(".").toList()
    } else {
        listOf(number.uppercase(), "")
    }

    // Conversion integer part of number
    val intPart = n[0].reversed()
    var intResult = BigInteger.ZERO
    var digit: Int

    for (i in intPart.indices) {
        digit = try {
            intPart[i].toString().toInt()
        } catch (e: NumberFormatException) {
            charDigit.indexOf(intPart[i].toString()) + 10
        }
        intResult += (digit * sourceBase.toDouble().pow(i.toDouble())).toLong().toBigInteger()
    }

    // Conversion fractional part of number if it exist,
    // else return only integer part of number conversion
    return if (n[1].isNotEmpty()) {
        var fractResult = BigDecimal.ZERO

        for (i in n[1].indices) {
            digit = try {
                n[1][i].toString().toInt()
            } catch (e: NumberFormatException) {
                charDigit.indexOf(n[1][i].toString()) + 10
            }
            fractResult += digit.toBigDecimal() * sourceBase.toBigDecimal()
                .pow(-(i + 1), MathContext(5))
        }

        "${intResult.toBigDecimal() +
                fractResult.setScale(5, RoundingMode.HALF_DOWN)}"

    } else {
        intResult.toString()
    }
}

// Conversion from base 10 to any base
fun decToAny (targetBase: Int, number: String) : String {
    val charDigit = ('A'..'Z').map { it.toString() }.toList()

    val n = if (number.contains('.')) {
        number.split(".").toList()
    } else {
        listOf(number, "")
    }

    // Conversion integer part of number
    var quotient = n[0].toBigInteger()
    var intResult = ""
    val base = targetBase.toBigInteger()

    while (quotient >= BigInteger.ONE) {
        intResult += if (base > BigInteger.TEN &&
            (quotient % base).toInt() > 9) {

            charDigit[((quotient % base).toInt() - 10)]
        } else (quotient % base).toString()
        quotient /= base
    }

    // Conversion fractional part of number if it exist,
    // else return only integer part of number conversion
    return if (n[1].isNotEmpty()) {
        var fractResult = ""
        var fractPart = ("0." + n[1]).toBigDecimal()
        var scale = fractPart.scale()
        var one = BigDecimal.ONE.setScale(scale, RoundingMode.HALF_DOWN)

        while (fractResult.length < 5) {
            fractPart *= base.toBigDecimal()

            fractResult += if (base > BigInteger.TEN &&
                fractPart.setScale(0, RoundingMode.DOWN).toInt() > 9) {
                charDigit[fractPart.setScale(0, RoundingMode.DOWN)
                    .toInt() - 10].lowercase()
            } else fractPart.setScale(0, RoundingMode.DOWN).toString()

            if (fractPart == one) break

            fractPart = fractPart.remainder(BigDecimal.ONE)
            scale = fractPart.scale()
            one = one.setScale(scale, RoundingMode.HALF_DOWN)
        }

        if (fractResult.length < 5) {
            fractResult += "0".repeat(5 - fractResult.length)
        }
        intResult.lowercase().reversed() +"."+ fractResult
    } else {
        intResult.reversed().lowercase()
    }
}