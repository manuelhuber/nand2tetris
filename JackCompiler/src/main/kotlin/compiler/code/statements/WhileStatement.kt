package compiler.code.statements

import compiler.JackAnalyzerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.expressions.Expression
import compiler.code.expressions.compileExpression
import utils.Keyword
import utils.Symbol
import utils.VmOperator

class WhileStatement(var condition: Expression, var statements: Statements) : Statement() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        val id = getUniqueNumber()
        val startLabel = "start$id"
        val endLabel = "end$id"

        label(startLabel)
        condition.compileToVm(this, symbols)
        add(VmOperator.Not)
        ifGoto(endLabel)

        statements.compileToVm(this, symbols)

        goto(startLabel)
        label(endLabel)
    }
}

fun JackAnalyzerDSL.compileWhileStatement(): WhileStatement {
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
