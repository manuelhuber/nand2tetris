package compiler

import compiler.tokenizer.Token


class Analyzer(private val tokens: List<Token>) {
    val jack: JackDSL = JackDSL(tokens)

    fun analyze(tokens: List<Token>) {

    }


}
