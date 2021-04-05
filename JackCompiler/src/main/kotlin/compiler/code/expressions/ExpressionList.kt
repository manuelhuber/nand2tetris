package compiler.code.expressions

import compiler.JackAnalyizerDSL
import utils.Symbol
import compiler.tokenizer.SymbolToken

class ExpressionList(val expressions: List<Expression> = emptyList())

fun JackAnalyizerDSL.compileExpressionList(): ExpressionList {
    return inTag("expressionList") {
        val list = mutableListOf(compileExpression())
        while (peak() is SymbolToken && peak().value[0] == Symbol.COMMA.value) {
            consumeSymbol(Symbol.COMMA)
            list.add(compileExpression())
        }
        ExpressionList(list)
    }
}
