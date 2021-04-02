package compiler.statements

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.expressions.Expression
import compiler.expressions.compileExpression

class WhileStatement(var expression: Expression, var statements: Statements)

fun JackDSL.compileWhileStatement(): WhileStatement {
    consumeKeyword(Keyword.WHILE)
    consumeSymbol(Symbol.ROUND_BRACKET_OPEN)
    val expression = compileExpression()
    consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)
    consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
    val statements = compileStatements()
    consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)
    return WhileStatement(expression, statements)
}
