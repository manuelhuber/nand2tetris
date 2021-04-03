package compiler

import compiler.programStructure.compileClass
import compiler.tokenizer.Token


class Analyzer(private val tokens: List<Token>) {
    val jack: JackDSL = JackDSL(tokens)

    fun analyze() {
        jack.analyze {
            compileClass()
        }
    }
}
