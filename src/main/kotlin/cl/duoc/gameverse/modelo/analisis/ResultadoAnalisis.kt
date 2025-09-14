package cl.duoc.gameverse.modelo.analisis

data class ResultadoAnalisis(val succesRate: Double, val analizado: Analizable) {
    override fun toString(): String {
        return "succesRate: $succesRate, analizado: $analizado"
    }
}
