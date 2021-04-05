package compiler.code.expressions

import compiler.JackAnalyzerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.SymbolToken
import utils.Symbol

class ExpressionList(val expressions: List<Expression> = emptyList()) : JackCode() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        expressions.forEach { expression ->
            expression.compileToVm(this, symbols)
        }
    }

    fun count(): Int {
        return expressions.count()
    }
}

fun JackAnalyzerDSL.compileExpressionList(): ExpressionList {
    return inTag("expressionList") {
        val list = mutableListOf(compileExpression())
        while (peak() is SymbolToken && peak().value[0] == Symbol.COMMA.value) {
            consumeSymbol(Symbol.COMMA)
            list.add(compileExpression())
        }
        ExpressionList(list)
    }
}
