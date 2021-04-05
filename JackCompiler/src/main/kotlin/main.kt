import assembler.Assembler
import compiler.Analyzer
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.Tokenizer
import vmTranslator.VmTranslator
import java.io.File
import java.nio.file.Path
import kotlin.io.path.*

@ExperimentalPathApi
fun main(args: Array<String>) {
    val path = Path.of(args[0])

    when {
        path.isDirectory() -> {
            path.listDirectoryEntries().filter { file -> file.extension == "jack" }.forEach { file ->
                compileJack(readFile(file.toString()), file.toAbsolutePath())
            }
            translateVmFiles(path)
        }
        else -> {
            val code = readFile(path.toString())
            when (path.extension) {
                "asm" -> {
                    writeFile(
                        Assembler().assemble(code),
                        path.toString().replaceAfter(".", "hack")
                    )
                }
                "vm" -> {
                    writeFile(
                        VmTranslator().translate(code, path.nameWithoutExtension),
                        path.toString().replaceAfter(".", "asm")
                    )
                }
                "jack" -> {
                    compileJack(code, path)
                }
            }
        }
    }
}

@ExperimentalPathApi
private fun translateVmFiles(path: Path) {
    val translator = VmTranslator(true)
    path.listDirectoryEntries().filter { file -> file.extension == "vm" }.forEach { file ->
        translator.translate(readFile(file.toString()), file.nameWithoutExtension)
    }
    writeFile(
        translator.getFullCode(),
        "$path/${path.fileName}.asm"
    )
}

fun compileJack(code: List<String>, path: Path) {
    val analyzer = Analyzer(Tokenizer().tokenize(code))
    val dsl = VmDSL()
    analyzer.analyze().compileToVm(dsl, SymbolTable())
    val output = dsl.code
    val outputPath = path.toString().replaceAfter(".", "vm")
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
