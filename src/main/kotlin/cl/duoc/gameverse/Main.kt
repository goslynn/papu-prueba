package cl.duoc.gameverse


import cl.duoc.gameverse.ui.MenuCli
import cl.duoc.gameverse.ui.OpcionMenu

fun main() {
    MenuCli.LEYENDA = false;
    val menu = MenuCli.forEnum<OpcionMenu>(label = { it.desc })
    val selected: OpcionMenu = menu.showMenu()
    println("Elegiste: $selected")
}