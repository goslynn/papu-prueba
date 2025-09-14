package cl.duoc.gameverse.ui

import kotlin.system.exitProcess

class MenuConsola(
    private val titulo: String = "Menú",
    private val prompt: String = "Elige opción (0 para salir): ",
    private val choices : Map<Int, OpcionMenu>
) {

    init {
        if (choices.size > 9) {
            throw IllegalArgumentException("Imposible tener un menu con mas de 9 opciones, pelotudo")
        }
    }

    /** Pinta encabezado y todas las opciones (BotonConsola). */
    private fun show() {
        println()
        println("=== $titulo ===")
        choices.forEach { println("${it.key}. ${it.value.description}") }
        print(prompt)
    }

    fun run() {
        loop@ while (true) {
            show()
            val input = readlnOrNull()?.trim().orEmpty()

            when {
                input.equals("q", true) || input == "0" -> {
                    println("Saliendo de la aplicación")
                    exitProcess(0) // o return para salir del método
                }
                input.toIntOrNull() == null -> {
                    System.err.println("Ingresa un número válido")
                    continue@loop
                }
                else -> {
                    val num = input.toInt()
                    try {
                        choices[num]?.event?.run()
                            ?: System.err.println("Opción inválida")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    data class OpcionMenu(val description: String, val event : Runnable?) {

        constructor(description: String) : this(description, null)

    }



}
