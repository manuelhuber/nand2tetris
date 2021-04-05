package compiler.code.programStructure

import compiler.JackAnalyizerDSL
import utils.Keyword
import utils.Symbol
import compiler.code.statements.Statements
import compiler.code.statements.compileStatements
import compiler.tokenizer.isA

class SubroutineBody(val varDecs: List<VarDec>, val statements: Statements)

fun JackAnalyizerDSL.compileSubroutineBody(): SubroutineBody {
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
