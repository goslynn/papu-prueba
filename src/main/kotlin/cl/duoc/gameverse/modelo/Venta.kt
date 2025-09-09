package cl.duoc.gameverse.modelo

data class Venta(
    val unitPrice : Double,
    val amount: Int) {

    fun total() : Double {
        return amount * unitPrice
    }

}