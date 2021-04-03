package compiler.statements

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.expressions.Expression
import compiler.expressions.compileExpression
import compiler.tokenizer.isA

class ReturnStatement(val expression: Expression? = null) : Statement()

fun JackDSL.compileReturnStatement(): ReturnStatement {
    return inTag("returnStatement") {
        consumeKeyword(Keyword.RETURN)
        val next = peak()
        val expression = if (!next.isA(Symbol.SEMICOLON)) {
            compileExpression()
        } else {
            null
        }
        consumeSymbol(Symbol.SEMICOLON)
        ReturnStatement(expression)
    }
}
