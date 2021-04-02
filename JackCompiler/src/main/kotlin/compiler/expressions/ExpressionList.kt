package compiler.expressions

import compiler.JackDSL
import compiler.Symbol
import compiler.tokenizer.SymbolToken

class ExpressionList(val expressions: List<Expression> = emptyList())

fun JackDSL.compileExpressionList(): ExpressionList {
    val list = mutableListOf(compileExpression())
    while (peak() is SymbolToken && peak().value[0] == Symbol.COMMA.value) {
        consumeSymbol(Symbol.COMMA)
        list.add(compileExpression())
    }
    return ExpressionList(list)
}
