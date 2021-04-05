import compiler.JackAnalyizerDSL
import compiler.tokenizer.Tokenizer

fun <T> testCompilation(code: String, kFunction1: JackAnalyizerDSL.() -> T): T {
    val tokens = Tokenizer().tokenize(listOf(code))
    val jackDSL = JackAnalyizerDSL(tokens)
    val analyze = jackDSL.analyze(kFunction1)
    jackDSL.compiledCodeAsXML.forEach(::println)
    return analyze
}
