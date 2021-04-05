package compiler.code.statements

import compiler.JackAnalyizerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import utils.Keyword
import utils.Symbol
import compiler.code.expressions.Expression
import compiler.code.expressions.compileExpression
import compiler.tokenizer.isA

class IfStatement(
    val condition: Expression, val trueStatements: Statements, val falseStatements: Statements? = null
) : Statement() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

fun JackAnalyizerDSL.compileIfStatement(): IfStatement {
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
