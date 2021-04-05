package compiler.code.expressions

import compiler.CompilationError
import compiler.JackAnalyzerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.*
import utils.*
import utils.UnaryOperator.MINUS
import utils.UnaryOperator.NEGATE
import utils.VmStack.*

sealed class Term : JackCode()

class IntegerTerm(val value: Int) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        push(CONSTANT, value)
    }
}

class StringTerm(val value: String) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        push(CONSTANT, value.length)
        call("String.new", 1)
        value.forEach { c ->
            push(CONSTANT, c.toInt())
            call("String.appendChar", 2)
        }
    }
}

class KeywordTerm(val value: Keyword) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        when (value) {
            Keyword.TRUE -> {
                push(CONSTANT, 1)
                add(VmOperator.Neg)
            }
            Keyword.FALSE -> push(CONSTANT, 0)
            Keyword.NULL -> push(CONSTANT, 0)
            else -> Unit
        }
    }
}

class VarNameTerm(val value: String) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        push(symbols.lookup(value))
    }
}

class ArrayVarNameTerm(val value: String, val ex: Expression) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        push(symbols.lookup(value))
        ex.compileToVm(this, symbols)
        add(VmOperator.Add)
        pop(POINTER, 1)
        push(THAT, 0)
    }
}

class ExpressionTerm(val value: Expression) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        value.compileToVm(this, symbols)
    }
}

class UnaryTerm(val operator: UnaryOperator, val term: Term) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        term.compileToVm(this, symbols)
        when (operator) {
            MINUS -> add(VmOperator.Neg)
            NEGATE -> add(VmOperator.Not)
        }
    }
}

class SubroutineCallTerm(val subroutineCall: SubroutineCall) : Term() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        subroutineCall.compileToVm(this, symbols)
    }
}


val validKeywordTerms = hashSetOf(Keyword.TRUE, Keyword.FALSE, Keyword.NULL, Keyword.THIS)

fun JackAnalyzerDSL.compileTerm(): Term {
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

private fun JackAnalyzerDSL.compileIdentifierToken(identifier: String): Term {
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

private fun JackAnalyzerDSL.compileSymbolToken(token: SymbolToken): Term {
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
