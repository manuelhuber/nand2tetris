package compiler

import compiler.code.programStructure.Class
import compiler.code.programStructure.compileClass
import compiler.tokenizer.Token


class Analyzer(tokens: List<Token>) {
    val jack: JackAnalyzerDSL = JackAnalyzerDSL(tokens)

    fun analyze(): Class {
        return jack.analyze {
            compileClass()
        }
    }
}
