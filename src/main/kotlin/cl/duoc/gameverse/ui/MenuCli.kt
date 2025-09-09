package cl.duoc.gameverse.ui;

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole
import org.jline.keymap.KeyMap
import org.jline.keymap.BindingReader
import org.jline.terminal.Attributes
import org.jline.terminal.Attributes.LocalFlag
import org.jline.terminal.Terminal
import org.jline.terminal.TerminalBuilder
import org.jline.utils.InfoCmp
import java.io.PrintWriter
import kotlin.system.exitProcess

class MenuCli<T>(
    private val options: Array<T>,
    private val label: (T) -> String,
    private val title: String = "=== MENÚ PRINCIPAL ==="
) {

    fun showMenu(): T {
        try { AnsiConsole.systemInstall() } catch (_: Throwable) {}

        val terminal: Terminal = TerminalBuilder.builder()
            .system(true)
            .jna(true)       // usa JNA si está disponible
            .jansi(true)     // integra jansi con jline
            .build()

        // Falla si JLine detecta terminal "dumb"
        if (terminal.type.lowercase().contains("dumb")) {
            try { AnsiConsole.systemUninstall() } catch (_: Throwable) {}
            error("No hay TTY real. Ejecuta en una terminal (no en la consola del IDE).")
        }

        val reader = BindingReader(terminal.reader())
        val keyMap = buildKeyMap()


        val prevAttrs = terminal.attributes
        val rawAttrs = Attributes(prevAttrs)
        rawAttrs.setLocalFlag(LocalFlag.ECHO, false)
        rawAttrs.setLocalFlag(LocalFlag.ICANON, false)
        terminal.attributes = rawAttrs
        val restored = { terminal.attributes = prevAttrs }

        var selected = 0
        render(terminal, title, options, label, selected)

        try {
            while (true) {
                when (val key = reader.readBinding(keyMap)) {
                    "UP" -> { selected = (selected - 1 + options.size) % options.size; render(terminal, title, options, label, selected) }
                    "DOWN" -> { selected = (selected + 1) % options.size; render(terminal, title, options, label, selected) }
                    "ENTER" -> {
                        restored(); AnsiConsole.systemUninstall()
                        return options[selected]
                    }
                    "QUIT" -> {
                        restored(); AnsiConsole.systemUninstall()
                        exitProcess(0)
                    }
                    else -> {
                        if (key != null && key.startsWith("NUM_")) {
                            val n = key.removePrefix("NUM_").toInt()
                            val idx = n - 1
                            if (idx in options.indices) {
                                restored(); AnsiConsole.systemUninstall()
                                return options[idx]
                            }
                        }
                    }
                }
            }
        } finally {
            try { restored() } catch (_: Throwable) {}
            try { AnsiConsole.systemUninstall() } catch (_: Throwable) {}
        }
    }

    private fun buildKeyMap(): KeyMap<String> {
        val map = KeyMap<String>()
        map.bind("UP", "\u001B[A")      // ESC [ A
        map.bind("DOWN", "\u001B[B")    // ESC [ B
        map.bind("ENTER", "\r", "\n")
        map.bind("QUIT", "q", "Q")
        for (n in 1..9) map.bind("NUM_$n", "$n")
        return map
    }

    private fun render(
        terminal: Terminal,
        title: String,
        options: Array<T>,
        label: (T) -> String,
        selected: Int
    ) {
        val out = terminal.writer()
        // Limpiar pantalla y mover cursor
        clearWithInfoCmp(terminal)
        out.flush()

        printLeyenda(out)

        out.println(Ansi.ansi()
            .fg(Ansi.Color.GREEN)
            .a(title)
            .reset())

        options.forEachIndexed { i, opt ->
            val selector = if (i == selected) ">" else " "
            val line = Ansi.ansi().apply {
                if (i == selected) {
                    it.fg(Ansi.Color.CYAN).a("$selector ")
                    it.fg(Ansi.Color.WHITE).a("${i + 1}. ${label(opt)}")
                } else {
                    it.fg(Ansi.Color.DEFAULT).a("$selector ")
                    it.fg(Ansi.Color.DEFAULT).a("${i + 1}. ${label(opt)}")
                }
                it.reset()
            }
            out.println(line)
        }
        out.flush()
    }

    private fun printLeyenda(out : PrintWriter) {
        if (!LEYENDA) return
        out.println(
            Ansi.ansi().fg(Ansi.Color.YELLOW)
                .a("--Leyenda-----------------------------------------------------------\n")
                .a("↑ -> navegar hacia arriba.\n")
                .a("↓ -> navegar hacia abajo.\n")
                .a("ENTER / (1..9) -> seleccion\n")
                .a("q -> cerrar (quit)\n")
                .a("--------------------------------------------------------------------")
                .reset())
        out.println()
    }

    private fun clearWithInfoCmp(terminal: Terminal) {
        terminal.puts(InfoCmp.Capability.clear_screen)
        terminal.flush()
    }

    companion object {
        var LEYENDA = true;

        inline fun <reified E : Enum<E>> forEnum(
            noinline label: (E) -> String,
            title: String = "=== MENÚ PRINCIPAL ==="
        ): MenuCli<E> = MenuCli(enumValues(), label, title)
    }
}
