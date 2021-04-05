package compiler.code.statements

import compiler.JackAnalyizerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import utils.Keyword
import utils.Symbol
import compiler.code.expressions.Expression
import compiler.code.expressions.compileExpression
import compiler.tokenizer.isA

class LetStatement(
    val varName: String, val expression: Expression, val indexExpression: Expression? = null
) : Statement() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

fun JackAnalyizerDSL.compileLetStatement(): LetStatement {
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
