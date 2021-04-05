package compiler.code.programStructure

import compiler.JackAnalyizerDSL
import utils.Symbol
import compiler.tokenizer.isA

class ParameterList(val pars: List<Pair<String, String>>)

fun JackAnalyizerDSL.compileParameterList(): ParameterList {
    return inTag("parameterList") {
        val pars = mutableListOf<Pair<String, String>>()
        while (!peak().isA(Symbol.ROUND_BRACKET_CLOSE)) {
            consumeSymbol(Symbol.COMMA)
            pars.add(Pair(compileType(), consumeIdentifier().value))
        }
        ParameterList(pars)
    }
}
