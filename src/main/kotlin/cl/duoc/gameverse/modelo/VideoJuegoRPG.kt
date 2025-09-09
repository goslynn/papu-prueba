package cl.duoc.gameverse.modelo

import java.time.LocalDate

class VideoJuegoRPG(
    title: String,
    price: Double,
    launchDate: LocalDate,
    rating: Double,
    var dlc: Int,
    var contentHours: Double,
) :
    VideoJuego(title, price, launchDate, rating) {

    override fun analizar(): Double {
        return rating * dlc * contentHours;
    }

}