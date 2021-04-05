import compiler.JackAnalyzerDSL
import compiler.tokenizer.Tokenizer

fun <T> testCompilation(code: String, kFunction1: JackAnalyzerDSL.() -> T): T {
    val tokens = Tokenizer().tokenize(listOf(code))
    val jackDSL = JackAnalyzerDSL(tokens)
    val analyze = jackDSL.analyze(kFunction1)
    jackDSL.compiledCodeAsXML.forEach(::println)
    return analyze
}
