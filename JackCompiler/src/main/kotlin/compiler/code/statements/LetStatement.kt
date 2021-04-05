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

class LetStatement(
    val varName: String, val expression: Expression, val indexExpression: Expression? = null
) : Statement() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        if (indexExpression == null) {
            expression.compileToVm(this, symbols)
            pop(symbols.lookup(varName))
        } else {
            push(symbols.lookup(varName))
            indexExpression.compileToVm(this, symbols)
            add(VmOperator.Add)
            expression.compileToVm(this, symbols)
            pop(VmStack.TEMP, 0)
            pop(VmStack.POINTER, 1)
            push(VmStack.TEMP, 0)
            pop(VmStack.THAT, 0)
        }
    }
}

fun JackAnalyzerDSL.compileLetStatement(): LetStatement {
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
