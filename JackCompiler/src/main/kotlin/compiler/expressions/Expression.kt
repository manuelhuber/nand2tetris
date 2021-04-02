package compiler.expressions

import compiler.JackDSL
import compiler.OPERATORS
import compiler.Operator
import compiler.tokenizer.SymbolToken

class Expression(val term: Term, val terms: List<Pair<Operator, Term>> = listOf())


fun JackDSL.compileExpression(): Expression {
    val firstTerm = compileTerm()
    val additionalTerms = mutableListOf<Pair<Operator, Term>>()
    while (peak() is SymbolToken && OPERATORS.contains(peak().value[0])) {
        val symbolToken = consumeSymbol(Operator.values().map { operator -> operator.value })
        val term = compileTerm()
        additionalTerms.add(Pair(Operator.fromValue(symbolToken.symbol), term))
    }
    return Expression(firstTerm, additionalTerms)
}
