import assembler.Assembler
import vmTranslator.VmTranslator
import java.io.File
import java.nio.file.Path
import kotlin.io.path.*

@ExperimentalPathApi
fun main(args: Array<String>) {
    val path = Path.of(args[0])

    val output: List<String>
    val outputPath: String
    when {
        path.extension == "asm" -> {
            val code = readFile(path.toString())
            output = Assembler().assemble(code)
            outputPath = path.toString().replaceAfter(".", "hack")
        }
        path.extension == "vm" -> {
            val code = readFile(path.toString())
            output = VmTranslator().translate(code, path.nameWithoutExtension)
            outputPath = path.toString().replaceAfter(".", "asm")

        }
        path.isDirectory() -> {
            val translator = VmTranslator(true)
            path.listDirectoryEntries().filter { file -> file.extension == "vm" }.forEach { file ->
                translator.translate(readFile(file.toString()), file.nameWithoutExtension)
            }
            output = translator.getFullCode()
            outputPath = "$path/${path.fileName}.asm"
        }
        else -> {
            throw Exception("Unknown filetype")
        }
    }

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
        writer.write(line)
        writer.write("\n")
    }
    writer.flush()
}
