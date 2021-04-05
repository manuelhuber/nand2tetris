package compiler.code.statements

import compiler.JackAnalyizerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import utils.Keyword
import utils.Symbol
import compiler.code.expressions.SubroutineCall
import compiler.code.expressions.compileSubroutineCall

class DoStatement(val subroutineCall: SubroutineCall) : Statement() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

fun JackAnalyizerDSL.compileDoStatement(): DoStatement {
    return inTag("doStatement") {
        consumeKeyword(Keyword.DO)
        val subroutineCall = compileSubroutineCall()
        consumeSymbol(Symbol.SEMICOLON)
        DoStatement(subroutineCall)
    }
}
