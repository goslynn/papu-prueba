package cl.duoc.gameverse.modelo

import java.time.LocalDate

class VideoJuegoIndie(
    title: String,
    price: Double,
    launchDate: LocalDate,
    rating: Double,
    var isInnovative : Boolean,
    var genres : Array<String>
) :
    VideoJuego(title, price, launchDate, rating) {

    override fun analizar(): Double {
        return rating * if (isInnovative) 1.8 else 1.0;
    }

}