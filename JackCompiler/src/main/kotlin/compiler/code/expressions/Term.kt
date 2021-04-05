package compiler.code.expressions

import compiler.CompilationError
import compiler.JackAnalyizerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.*
import utils.Keyword
import utils.Symbol
import utils.UNARY_OPERATORS
import utils.UnaryOperator
import utils.VmStack.CONSTANT

sealed class Term : JackCode()

class IntegerTerm(val value: Int) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        push(CONSTANT, value)
    }
}

class StringTerm(val value: String) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

class KeywordTerm(val value: Keyword) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        // Nothing to do
    }
}

class VarNameTerm(val value: String) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

class ArrayVarNameTerm(val value: String, val ex: Expression) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

class ExpressionTerm(val value: Expression) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

class UnaryTerm(val operator: UnaryOperator, val term: Term) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}

class SubroutineCallTerm(val subroutineCall: SubroutineCall) : Term() {
    override fun VmDSL.toVmCode(symbols: SymbolTable) {
        TODO("Not yet implemented")
    }
}


val validKeywordTerms = hashSetOf(Keyword.TRUE, Keyword.FALSE, Keyword.NULL, Keyword.THIS)

fun JackAnalyizerDSL.compileTerm(): Term {
    return inTag("term") {
        val token = consumeToken()
        val value = token.value
        when (token) {
            is StringConstantToken -> StringTerm(value)
            is IntegerConstantToken -> IntegerTerm(token.int)
            is KeywordToken -> {
                if (validKeywordTerms.contains(token.keyword)) KeywordTerm(token.keyword)
                else throw CompilationError("Unexpected keyword ${token.keyword.value}")
            }
            is IdentifierToken -> compileIdentifierToken(value)
            is SymbolToken -> compileSymbolToken(token)
        }
    }
}

private fun JackAnalyizerDSL.compileIdentifierToken(identifier: String): Term {
    val nextToken = peak()
    val basicVarNameTerm = VarNameTerm(identifier)
    return when {
        nextToken !is SymbolToken -> basicVarNameTerm
        nextToken.symbol == Symbol.SQUARE_BRACKET_OPEN -> {
            consumeSymbol(Symbol.SQUARE_BRACKET_OPEN)
            val expression = compileExpression()
            consumeSymbol(Symbol.SQUARE_BRACKET_CLOSE)
            ArrayVarNameTerm(identifier, expression)
        }
        nextToken.symbol == Symbol.DOT || nextToken.symbol == Symbol.ROUND_BRACKET_OPEN -> {
            backpaddle() // undo consuming the identifier
            SubroutineCallTerm(compileSubroutineCall())
        }
        else -> basicVarNameTerm
    }
}

private fun JackAnalyizerDSL.compileSymbolToken(token: SymbolToken): Term {
    return when {
        token.symbol == Symbol.ROUND_BRACKET_OPEN -> {
            val expression = compileExpression()
            consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)
            ExpressionTerm(expression)
        }
        UNARY_OPERATORS.contains(token.symbol) -> {
            val term = compileTerm()
            UnaryTerm(UnaryOperator.fromValue(token.symbol), term)
        }
        else -> throw CompilationError("Expected valid term, but got symbol ${token.value}")
    }
}
