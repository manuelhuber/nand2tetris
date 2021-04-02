package compiler.statements

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.expressions.Expression
import compiler.expressions.compileExpression
import compiler.tokenizer.SymbolToken

class ReturnStatement(val expression: Expression? = null)

fun JackDSL.compileReturnStatement(): ReturnStatement {
    consumeKeyword(Keyword.RETURN)
    val next = peak()
    val expression = if (next is SymbolToken && next.symbol == Symbol.COMMA) {
        compileExpression()
    } else {
        null
    }
    return ReturnStatement(expression)
}
