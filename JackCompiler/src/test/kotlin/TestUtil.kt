import compiler.JackDSL
import compiler.tokenizer.Tokenizer

fun <T> testCompilation(code: String, kFunction1: JackDSL.() -> T): T {
    val tokens = Tokenizer().tokenize(listOf(code))
    val jackDSL = JackDSL(tokens)
    val analyze = jackDSL.analyze(kFunction1)
    jackDSL.compiledCode.forEach(::println)
    return analyze
}
