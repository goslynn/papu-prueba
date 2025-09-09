package cl.duoc.gameverse.modelo

import java.time.LocalDate

class VideoJuegoAccion(
    title: String,
    price: Double,
    launchDate: LocalDate,
    rating: Double,
    var budget: Double
) : VideoJuego(title, price, launchDate, rating) {

    lateinit var sales : Venta;

    constructor(
        title: String,
        price: Double,
        launchDate: LocalDate,
        rating: Double,
        budget: Double,
        totalSales: Double
    ) : this(title, price, launchDate, rating, budget) {
        this.sales = Venta(price, totalSales.div(price).toInt())
    }

    constructor(
        title: String,
        price: Double,
        launchDate: LocalDate,
        rating: Double,
        budget: Double,
        soldCopys: Int
    ) : this(title, price, launchDate, rating, budget) {
        this.sales = Venta(price, soldCopys)
    }

    constructor(
        title: String,
        price: Double,
        launchDate: LocalDate,
        rating: Double,
        budget: Double,
        sales: Venta
    ) : this(title, price, launchDate, rating, budget) {
        this.sales = sales
    }

    override fun analizar(): Double {
        return (rating * sales.total()) / budget;
    }

}