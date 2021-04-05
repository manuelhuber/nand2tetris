package compiler.code.programStructure

import compiler.JackAnalyzerDSL
import utils.Keyword
import utils.Symbol
import compiler.tokenizer.isA

class VarDec(val type: String, val names: List<String>)

fun JackAnalyzerDSL.compileVarDec(): VarDec {
    return inTag("varDec") {
        consumeKeyword(Keyword.VAR)
        val type = compileType()
        val names = mutableListOf(consumeIdentifier().value)
        while (peak().isA(Symbol.COMMA)) {
            consumeSymbol(Symbol.COMMA)
            names.add(consumeIdentifier().value)
        }
        consumeSymbol(Symbol.SEMICOLON)

        VarDec(type, names)
    }
}
