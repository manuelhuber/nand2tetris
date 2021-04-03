package compiler.programStructure

import compiler.JackDSL
import compiler.Keyword
import compiler.Symbol
import compiler.statements.Statements
import compiler.statements.compileStatements
import compiler.tokenizer.isA

class SubroutineBody(val varDecs: List<VarDec>, val statements: Statements)

fun JackDSL.compileSubroutineBody(): SubroutineBody {
    return inTag("subroutineBody") {
        consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
        val varDec = mutableListOf<VarDec>()
        while (peak().isA(Keyword.VAR)) {
            varDec.add(compileVarDec())
        }
        val statements = compileStatements()
        consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)
        SubroutineBody(varDec, statements)
    }
}
