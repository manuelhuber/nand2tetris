package compiler.statements

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.expressions.SubroutineCall
import compiler.expressions.compileSubroutineCall

class DoStatement(val subroutineCall: SubroutineCall) : Statement()

fun JackDSL.compileDoStatement(): DoStatement {
    return inTag("doStatement") {
        consumeKeyword(Keyword.DO)
        val subroutineCall = compileSubroutineCall()
        consumeSymbol(Symbol.SEMICOLON)
        DoStatement(subroutineCall)
    }
}
