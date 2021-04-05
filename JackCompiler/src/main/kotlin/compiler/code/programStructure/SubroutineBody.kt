package compiler.code.programStructure

import compiler.JackAnalyzerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.statements.Statements
import compiler.code.statements.compileStatements
import compiler.tokenizer.isA
import utils.Keyword
import utils.Symbol

class SubroutineBody(val varDecs: List<VarDec>, val statements: Statements) : JackCode() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        statements.compileToVm(this, symbols)
    }
}

fun JackAnalyzerDSL.compileSubroutineBody(): SubroutineBody {
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
