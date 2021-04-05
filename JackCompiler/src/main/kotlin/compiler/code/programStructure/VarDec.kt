package compiler.code.programStructure

import compiler.JackAnalyzerDSL
import compiler.tokenizer.isA
import utils.Keyword
import utils.Symbol

class VarDec(val fieldType: Keyword, val type: String, val names: List<String>)

fun JackAnalyzerDSL.compileVarDec(): VarDec {
    return inTag("varDec") {
        val fieldType = consumeKeyword(listOf(Keyword.VAR, Keyword.FIELD, Keyword.STATIC))
        val type = compileType()
        val names = mutableListOf(consumeIdentifier().value)
        while (peak().isA(Symbol.COMMA)) {
            consumeSymbol(Symbol.COMMA)
            names.add(consumeIdentifier().value)
        }
        consumeSymbol(Symbol.SEMICOLON)

        VarDec(fieldType.keyword, type, names)
    }
}
