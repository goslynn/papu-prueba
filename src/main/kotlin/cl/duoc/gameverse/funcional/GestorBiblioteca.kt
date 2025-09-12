package cl.duoc.gameverse.funcional

import cl.duoc.gameverse.modelo.VideoJuego
import cl.duoc.gameverse.modelo.VideoJuegoAccion
import cl.duoc.gameverse.modelo.VideoJuegoIndie
import cl.duoc.gameverse.modelo.VideoJuegoRPG
import cl.duoc.gameverse.modelo.analisis.Analizable
import java.time.LocalDate

class GestorBiblioteca {
    companion object {
        private val DATABASE: List<VideoJuego> = database()

        private fun database(): List<VideoJuego> =
            listOf(
                VideoJuegoRPG(
                    "The Witcher 3",
                    39.99,
                    LocalDate.of(2015, 5, 19),
                    9.3,
                    2,
                    120.0
                ),
                VideoJuegoRPG(
                    "Elden Ring",
                    59.99,
                    LocalDate.of(2022, 2, 25),
                    9.5,
                    1,
                    100.0
                ),
                VideoJuegoAccion(
                    "Cyberpunk 2077",
                    49.99,
                    LocalDate.of(2020, 12, 10),
                    7.8,
                    174_000_000.0,
                    780_000_000.0
                ),
                VideoJuegoAccion(
                    "Call of Duty: MW",
                    69.99,
                    LocalDate.of(2023, 10, 28),
                    8.2,
                    250_000_000.0,
                    1_200_000_000.0
                ),
                VideoJuegoIndie(
                    "Hades",
                    24.99,
                    LocalDate.of(2020, 9, 17),
                    9.0,
                    true,
                    arrayOf("Mitología", "Roguelike")
                ),
                VideoJuegoIndie(
                    "Hollow Knight",
                    14.99,
                    LocalDate.of(2017, 2, 24),
                    8.7,
                    true,
                    arrayOf("Metroidvania", "Plataformas")
                )
            )

        fun getDatabase(): MutableList<VideoJuego> = DATABASE.toMutableList()

    }


    fun orderBySuccessAscending(listado: Collection<Analizable>): List<Analizable> {
        return listado.sortedBy{it.analizar()}
    }

    fun orderBySuccessDescending(listado: Collection<Analizable>): List<Analizable> {
        return listado.sortedByDescending{ it.analizar() }
    }

    fun getTopN(listado: Collection<Analizable>, top : Int): List<Analizable> {
        return orderBySuccessDescending(listado).subList(0, top);
    }

    fun getBottomN(listado: Collection<Analizable>, bottom : Int): List<Analizable> {
        return orderBySuccessAscending(listado).subList(0, bottom);
    }






}