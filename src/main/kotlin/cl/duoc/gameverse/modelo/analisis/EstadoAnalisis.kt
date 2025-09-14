package cl.duoc.gameverse.modelo.analisis

sealed class EstadoAnalisis(val msg: String) {
    data object Iniciando : EstadoAnalisis("Iniciando analisis")
    data object Analizando: EstadoAnalisis("Analizando algoritmos de exito")
    data object Completado : EstadoAnalisis("Finalizamos analisis")
    data object Fallido : EstadoAnalisis("Fallamos analisis")
}