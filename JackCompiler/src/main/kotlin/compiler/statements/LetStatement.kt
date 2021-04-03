package compiler.statements

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.expressions.Expression
import compiler.expressions.compileExpression
import compiler.tokenizer.isA

class LetStatement(
    val varName: String, val expression: Expression, val indexExpression: Expression? = null
) : Statement()

fun JackDSL.compileLetStatement(): LetStatement {
    return inTag("letStatement") {

        consumeKeyword(Keyword.LET)
        val varName = consumeIdentifier()

        var indexExpression: Expression? = null
        if (peak().isA(Symbol.SQUARE_BRACKET_OPEN)) {
            consumeSymbol(Symbol.SQUARE_BRACKET_OPEN)
            indexExpression = compileExpression()
            consumeSymbol(Symbol.SQUARE_BRACKET_CLOSE)
        }

        consumeSymbol(Symbol.EQUAL)
        val expression = compileExpression()
        consumeSymbol(Symbol.SEMICOLON)

        LetStatement(varName.value, expression, indexExpression)
    }
}
