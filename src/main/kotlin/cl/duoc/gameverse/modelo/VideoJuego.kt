package cl.duoc.gameverse.modelo

import cl.duoc.gameverse.modelo.analisis.Analizable
import java.time.LocalDate


abstract class VideoJuego(
    val title: String,
    val price: Double,
    val launchDate: LocalDate,
    val rating: Double
) : Analizable {

    companion object {
        const val MIN_RATING = 0.0
        const val MAX_RATING = 10.0
    }

    init {
        if (this.rating !in (MIN_RATING..MAX_RATING)) {
            throw IllegalArgumentException("El rating debe estar entre $MIN_RATING y $MAX_RATING")
        }
    }

}