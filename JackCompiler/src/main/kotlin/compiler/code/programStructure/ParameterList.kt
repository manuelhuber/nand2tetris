package compiler.code.programStructure

import compiler.JackAnalyzerDSL
import compiler.tokenizer.isA
import utils.Symbol

class Parameter(val type: String, val identifier: String)
class ParameterList(val pars: List<Parameter>) {
    fun count(): Int {
        return pars.count()
    }
}

fun JackAnalyzerDSL.compileParameterList(): ParameterList {
    return inTag("parameterList") {
        val pars = mutableListOf<Parameter>()
        var first = true
        while (!peak().isA(Symbol.ROUND_BRACKET_CLOSE)) {
            if (first) {
                first = false
            } else {
                consumeSymbol(Symbol.COMMA)
            }
            pars.add(Parameter(compileType(), consumeIdentifier().value))
        }
        ParameterList(pars)
    }
}
