package compiler.code.statements

import compiler.JackAnalyzerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import utils.Keyword
import utils.Symbol
import compiler.code.expressions.SubroutineCall
import compiler.code.expressions.compileSubroutineCall

class DoStatement(val subroutineCall: SubroutineCall) : Statement() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
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
