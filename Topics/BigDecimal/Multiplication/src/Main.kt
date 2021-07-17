fun main() {
    val (a, b) = List(2) { readLine()!!.toBigDecimal() }
    println(a * b)
}