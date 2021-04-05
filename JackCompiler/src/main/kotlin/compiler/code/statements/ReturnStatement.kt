package compiler.code.statements

import compiler.JackAnalyzerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.expressions.Expression
import compiler.code.expressions.compileExpression
import compiler.tokenizer.isA
import utils.Keyword
import utils.Symbol
import utils.VmOperator
import utils.VmStack

class ReturnStatement(val expression: Expression? = null) : Statement() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        if (expression == null) {
            push(VmStack.CONSTANT, 0)
        } else {
            expression.compileToVm(this, symbols)
        }

        add(VmOperator.Return)
    }
}

fun JackAnalyzerDSL.compileReturnStatement(): ReturnStatement {
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
