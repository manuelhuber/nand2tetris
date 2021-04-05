package compiler.code.programStructure

import compiler.JackAnalyizerDSL
import utils.Keyword
import utils.Symbol
import compiler.tokenizer.isA

class Class(val name: String, val vars: List<ClassVarDec>, val subroutines: List<SubroutineDec>)

fun JackAnalyizerDSL.compileClass(): Class {
    return inTag("class") {
        consumeKeyword(Keyword.CLASS)
        val name = consumeIdentifier().value
        consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
        val vars = mutableListOf<ClassVarDec>()
        while (peak().isA(Keyword.STATIC) || peak().isA(Keyword.FIELD)) {
            vars.add(compileClassVarDec())
        }
        val subroutines = mutableListOf<SubroutineDec>()
        while (!peak().isA(Symbol.CURLY_BRACKET_CLOSE)) {
            subroutines.add(compileSubroutineDec())
        }
        consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)
        Class(name, vars, subroutines)
    }
}