package compiler.code.expressions

import compiler.JackAnalyzerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.SymbolToken
import utils.OPERATORS
import utils.Operator
import utils.Operator.*
import utils.VmOperator.*

class Expression(val term: Term, val terms: List<Pair<Operator, Term>> = listOf()) : JackCode() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        term.compileToVm(this, symbols)
        terms.forEach { pair ->
            pair.second.compileToVm(this, symbols)
            translateOperator(pair.first)
        }
    }
}

fun VmDSL.translateOperator(operator: Operator) {
    when (operator) {
        EQUAL -> add(Eq)
        PLUS -> add(Add)
        MINUS -> {
            add(Neg)
            add(Add)
        }
        STAR -> call("Math.multiply", 2)
        SLASH -> call("Math.divide", 2)
        AMPERSAND -> add(And)
        PIPE -> add(Or)
        LESS_THAN -> add(Lt)
        GREATER_THAN -> add(Gt)
    }

}


fun JackAnalyzerDSL.compileExpression(): Expression {
    return inTag("expression") {
        val firstTerm = compileTerm()
        val additionalTerms = mutableListOf<Pair<Operator, Term>>()
        while (peak() is SymbolToken && OPERATORS.contains(peak().value[0])) {
            val symbolToken = consumeSymbol(Operator.values().map { operator -> operator.value })
            val term = compileTerm()
            additionalTerms.add(Pair(Operator.fromValue(symbolToken.symbol), term))
        }
        Expression(firstTerm, additionalTerms)
    }
}
