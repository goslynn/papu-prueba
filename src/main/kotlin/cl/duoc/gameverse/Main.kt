package cl.duoc.gameverse

import cl.duoc.gameverse.funcional.GestorBiblioteca
import cl.duoc.gameverse.ui.MenuConsola


fun main() {
    val menu = MenuConsola( titulo = "Biblioteca GameVerse", choices = getOpcionesMenu(GestorBiblioteca()))
    menu.run()
}

//TODO : Implementar las acciones reales.
fun getOpcionesMenu(gestor : GestorBiblioteca) : Map<Int, MenuConsola.OpcionMenu> {
    return mapOf(
        Pair(1,
        MenuConsola.OpcionMenu("Ver biblioteca completa") { print("hola chavales") }),
        Pair(2,
            MenuConsola.OpcionMenu("Analisis de exito (Async)") { print("hola exito") }),
        Pair(3,
            MenuConsola.OpcionMenu("Filtrar por año") {print("que carajo que mierda")}),
        Pair(4,
            MenuConsola.OpcionMenu("Estadisticas ed la biblioteca") {print("saludos cordiales")}))
}