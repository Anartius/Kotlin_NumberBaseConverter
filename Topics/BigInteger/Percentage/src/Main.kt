import java.math.BigInteger        

fun main() {
    
    val (a, b) = Array(2) { readLine()!!.toBigInteger() }
    val hundred = BigInteger("100")
    val sum = a.add(b)
    
    val numToPercent1 = (a * hundred).divide(sum)
    val numToPercent2 = (b * hundred).divide(sum)
    
    print("$numToPercent1% $numToPercent2%")
}
