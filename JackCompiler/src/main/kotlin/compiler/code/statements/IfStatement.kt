package compiler.code.statements

import compiler.JackAnalyzerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.expressions.Expression
import compiler.code.expressions.compileExpression
import compiler.tokenizer.isA
import utils.Keyword
import utils.Symbol

class IfStatement(
    val condition: Expression, val trueStatements: Statements, val falseStatements: Statements? = null
) : Statement() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        val id = getUniqueNumber()
        val trueLabel = "true$id"
        val endLabel = "end$id"

        condition.compileToVm(this, symbols)
        ifGoto(trueLabel)
        falseStatements?.compileToVm(this, symbols)
        goto(endLabel)
        label(trueLabel)
        trueStatements.compileToVm(this, symbols)
        label(endLabel)

    }
}

fun JackAnalyzerDSL.compileIfStatement(): IfStatement {
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
