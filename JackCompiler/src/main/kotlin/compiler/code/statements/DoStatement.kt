package compiler.code.statements

import compiler.JackAnalyzerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.expressions.SubroutineCall
import compiler.code.expressions.compileSubroutineCall
import utils.Keyword
import utils.Symbol
import utils.VmStack

class DoStatement(val subroutineCall: SubroutineCall) : Statement() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        subroutineCall.compileToVm(this, symbols)
        pop(VmStack.TEMP, 0)
    }
}

fun JackAnalyzerDSL.compileDoStatement(): DoStatement {
    return inTag("doStatement") {
        consumeKeyword(Keyword.DO)
        val subroutineCall = compileSubroutineCall()
        consumeSymbol(Symbol.SEMICOLON)
        DoStatement(subroutineCall)
    }
}
