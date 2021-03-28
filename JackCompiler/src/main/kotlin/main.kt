import assembler.Assembler
import vmTranslator.VmTranslator
import java.io.File
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.nameWithoutExtension

@ExperimentalPathApi
fun main(args: Array<String>) {
    val path = args[0]
    val code = readFile(path)

    val output: List<String>
    val outputExtension: String
    when {
        path.endsWith(".asm") -> {
            output = Assembler().assemble(code)
            outputExtension = "hack"
        }
        path.endsWith(".vm") -> {
            output = VmTranslator().translate(code, Path.of(path).nameWithoutExtension)
            outputExtension = "asm"
        }
        else -> {
            throw Exception("Unknown filetype")
        }
    }

    val outputPath = path.replaceAfter(".", outputExtension)
    writeFile(output, outputPath)
}

fun readFile(path: String): List<String> {
    return File(path).readLines().map { s -> s.trim() }
}

fun writeFile(lines: List<String>, path: String) {
    val file = File(path)
    file.createNewFile()
    val writer = file.writer()
    lines.forEach { line ->
        writer.write(line);
        writer.write("\n")
    }
    writer.flush()
}
