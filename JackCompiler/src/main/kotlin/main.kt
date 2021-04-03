import assembler.Assembler
import compiler.Analyzer
import compiler.tokenizer.Tokenizer
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
        path.isDirectory() -> {
            val translator = VmTranslator(true)
            path.listDirectoryEntries().filter { file -> file.extension == "vm" }.forEach { file ->
                translator.translate(readFile(file.toString()), file.nameWithoutExtension)
            }
            output = translator.getFullCode()
            outputPath = "$path/${path.fileName}.asm"
        }
        else -> {
            val code = readFile(path.toString())
            when (path.extension) {
                "asm" -> {
                    output = Assembler().assemble(code)
                    outputPath = path.toString().replaceAfter(".", "hack")
                }
                "vm" -> {
                    output = VmTranslator().translate(code, path.nameWithoutExtension)
                    outputPath = path.toString().replaceAfter(".", "asm")
                }
                "jack" -> {
                    val analyzer = Analyzer(Tokenizer().tokenize(code))
                    analyzer.analyze()
                    output = analyzer.jack.compiledCodeAsXML
                    outputPath = path.toString().replaceAfter(".", "tokens.xml")
                }
                else -> {
                    throw Exception("Unknown filetype")
                }
            }
        }
    }
    if (outputPath.isNotEmpty()) {
        writeFile(output, outputPath)
    }
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
