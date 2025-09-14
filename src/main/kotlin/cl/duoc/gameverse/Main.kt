package cl.duoc.gameverse

import cl.duoc.gameverse.funcional.GestorBiblioteca
import cl.duoc.gameverse.modelo.VideoJuego
import cl.duoc.gameverse.modelo.analisis.EstadoAnalisis
import cl.duoc.gameverse.ui.MenuConsola
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.stream.Collectors


fun main() {
    val menu = MenuConsola( titulo = "Biblioteca GameVerse", choices = getOpcionesMenu(GestorBiblioteca(), GestorBiblioteca.getDatabase()))
    menu.run()
}

fun notificarEstado(estado: EstadoAnalisis) {
    println("${estado.msg}...")
}

//TODO : Implementar las acciones reales.
fun getOpcionesMenu(gestor : GestorBiblioteca, biblio : List<VideoJuego>) : Map<Int, MenuConsola.OpcionMenu> {
    return mapOf(
        Pair(
            1,
            MenuConsola.OpcionMenu("Ver biblioteca completa") {
                biblio.forEach { println(it.toString()) }
            }),
        Pair(
            2,
            MenuConsola.OpcionMenu("Analisis de exito (Async)") {
                runBlocking {
                    println("COMENZANDO ANALISIS")

                    val estadosJob = launch {
                        notificarEstado(EstadoAnalisis.Iniciando)
                        delay(2_000)
                        notificarEstado(EstadoAnalisis.Analizando)
                        delay(2_000)
                        notificarEstado(EstadoAnalisis.Completado)
                        delay(2_000)
                    }

                    val top1Deferred = async(Dispatchers.Default) {
                        gestor.getTopN(biblio, 1)[0]
                    }

                    estadosJob.join()

                    println("ANALISIS COMPLETADO")
                    val top1 = top1Deferred.await()
                    println("JUEGO MAS EXITOSO $top1")
                }
            }),
        Pair(
            3,
            MenuConsola.OpcionMenu("Filtrar por año") {
                print("ingresa el año a filtrar:");
                val input = readlnOrNull()?.trim().orEmpty()
                when {
                    !Regex("^\\d{4}$").matches(input) -> {
                        System.err.println("Ingresa un año válido")
                        return@OpcionMenu
                    }
                    else -> {
                        val y = input.toInt()
                        val vj = biblio.stream()
                            .filter{it.launchDate.year == y}
                            .collect(Collectors.toList())
                        if (vj.isEmpty()) {
                            println("no tenemos un videojuego lanzado en el año ${y}. lo sentimos :(")
                            return@OpcionMenu
                        }
                        else {
                            vj.forEach{println(it)}
                        }

                    }
                }
            }),
        Pair(
            4,
            MenuConsola.OpcionMenu("Estadisticas ed la biblioteca") {
                println("--TOP VIDEOJUEGOS--")
                gestor.orderBySuccessDescending(biblio)
                    .forEachIndexed { i, it ->
                        println("${i+1}. $it")
                    }
            })
    )
}