package compiler.code.statements

import compiler.JackAnalyizerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import utils.Keyword
import utils.Symbol
import compiler.code.expressions.Expression
import compiler.code.expressions.compileExpression
import compiler.tokenizer.isA

class ReturnStatement(val expression: Expression? = null) : Statement() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

fun JackAnalyizerDSL.compileReturnStatement(): ReturnStatement {
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
