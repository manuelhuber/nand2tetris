package compiler.programStructure

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.tokenizer.isA

class VarDec(val type: String, val names: List<String>)

fun JackDSL.compileVarDec(): VarDec {
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
