package cl.duoc.gameverse.modelo.analisis

sealed class EstadoAnalisis(val progress: Int) {
    data object Iniciando : EstadoAnalisis(25)
    data object Analizando: EstadoAnalisis(50)
    data object Completado : EstadoAnalisis(75)
    data object Fallido : EstadoAnalisis(100)
}