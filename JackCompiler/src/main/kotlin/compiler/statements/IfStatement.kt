package compiler.statements

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.expressions.Expression
import compiler.expressions.compileExpression
import compiler.tokenizer.isA

class IfStatement(
    val condition: Expression, val trueStatements: Statements, val falseStatements: Statements? = null
) : Statement()

fun JackDSL.compileIfStatement(): IfStatement {
    return inTag("ifStatement") {

        consumeKeyword(Keyword.IF)
        consumeSymbol(Symbol.ROUND_BRACKET_OPEN)
        val condition = compileExpression()
        consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)
        consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
        val trueStatements = compileStatements()
        consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)

        var falseStatements: Statements? = null
        if (peak().isA(Keyword.ELSE)) {
            consumeKeyword(Keyword.ELSE)
            consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
            falseStatements = compileStatements()
            consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)
        }

        IfStatement(condition, trueStatements, falseStatements)
    }
}
