package compiler.code.statements

import compiler.JackAnalyizerDSL
import compiler.NotAStatement
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.KeywordToken
import utils.Keyword

class Statements(val statements: List<Statement>) : JackCode() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

abstract class Statement : JackCode()

fun JackAnalyizerDSL.compileStatements(): Statements {
    return inTag("statements") {
        val statements = mutableListOf<Statement>()
        var moreStatements = true
        while (moreStatements) {
            try {
                statements.add(compileStatement())
            } catch (e: NotAStatement) {
                moreStatements = false
            }
        }
        Statements(statements)
    }
}

fun JackAnalyizerDSL.compileStatement(): Statement {
    val next = peak()
    if (next !is KeywordToken) throw  NotAStatement("Unexpected token $next")
    return when (next.keyword) {
        Keyword.LET -> compileLetStatement()
        Keyword.IF -> compileIfStatement()
        Keyword.WHILE -> compileWhileStatement()
        Keyword.DO -> compileDoStatement()
        Keyword.RETURN -> compileReturnStatement()
        else -> throw NotAStatement("Unexpected keyword ${next.keyword}")
    }
}

