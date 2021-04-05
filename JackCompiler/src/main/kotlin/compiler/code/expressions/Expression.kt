package compiler.code.expressions

import compiler.JackAnalyizerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.SymbolToken
import utils.OPERATORS
import utils.Operator
import utils.Operator.*
import utils.VmOperators

class Expression(val term: Term, val terms: List<Pair<Operator, Term>> = listOf()) : JackCode() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        term.toVmCodeX(this, symbols)
        terms.forEach { pair ->
            pair.second.toVmCodeX(this, symbols)
            when (pair.first) {
                EQUAL -> add(VmOperators.Eq.value)
                PLUS -> TODO()
                MINUS -> TODO()
                STAR -> TODO()
                SLASH -> TODO()
                AMPERSAND -> TODO()
                PIPE -> TODO()
                LESS_THAN -> TODO()
                GREATER_THAN -> TODO()
            }
        }
    }

}


fun JackAnalyizerDSL.compileExpression(): Expression {
    return inTag("expression") {
        val firstTerm = compileTerm()
        val additionalTerms = mutableListOf<Pair<Operator, Term>>()
        while (peak() is SymbolToken && OPERATORS.contains(peak().value[0])) {
            val symbolToken = consumeSymbol(values().map { operator -> operator.value })
            val term = compileTerm()
            additionalTerms.add(Pair(Operator.fromValue(symbolToken.symbol), term))
        }
        Expression(firstTerm, additionalTerms)
    }
}
