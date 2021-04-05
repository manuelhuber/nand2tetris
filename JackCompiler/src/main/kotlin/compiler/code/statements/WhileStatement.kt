package compiler.code.statements

import compiler.JackAnalyizerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import utils.Keyword
import utils.Symbol
import compiler.code.expressions.Expression
import compiler.code.expressions.compileExpression

class WhileStatement(var condition: Expression, var statements: Statements) : Statement() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

fun JackAnalyizerDSL.compileWhileStatement(): WhileStatement {
    return inTag("whileStatement") {
        consumeKeyword(Keyword.WHILE)
        consumeSymbol(Symbol.ROUND_BRACKET_OPEN)
        val expression = compileExpression()
        consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)
        consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
        val statements = compileStatements()
        consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)
        WhileStatement(expression, statements)
    }
}
