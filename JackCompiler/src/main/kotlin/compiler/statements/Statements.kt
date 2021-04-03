package compiler.statements

import compiler.JackCode
import compiler.JackDSL
import compiler.Keyword
import compiler.NotAStatement
import compiler.tokenizer.KeywordToken

class Statements(val statements: List<Statement>) : JackCode()
abstract class Statement : JackCode()

fun JackDSL.compileStatements(): Statements {
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

fun JackDSL.compileStatement(): Statement {
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

