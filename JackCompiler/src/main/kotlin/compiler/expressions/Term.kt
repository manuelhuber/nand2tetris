package compiler.expressions

import compiler.*
import compiler.tokenizer.*

sealed class Term

class IntegerTerm(val value: Int) : Term() {
    override fun toString(): String {
        return "IntegerConstant(value=$value)"
    }
}

class StringTerm(val value: String) : Term() {
    override fun toString(): String {
        return "StringConstant(value='$value')"
    }
}

class KeywordTerm(val value: Keyword) : Term() {
    override fun toString(): String {
        return "KeywordConstant(value='$value')"
    }
}

class VarNameTerm(val value: String) : Term() {
    override fun toString(): String {
        return "VarName(value='$value')"
    }
}

class ArrayVarNameTerm(val value: String, val ex: Expression) : Term() {
    override fun toString(): String {
        return "ArrayVarName(value='$value', ex=$ex)"
    }
}

class ExpressionTerm(val value: Expression) : Term() {
    override fun toString(): String {
        return "ExpressionTerm(value=$value)"
    }
}

class UnaryTerm(val operator: UnaryOperator, val term: Term) : Term() {
    override fun toString(): String {
        return "UnaryTerm(operator=$operator, term=$term)"
    }
}

class SubroutineCallTerm(val subroutineCall: SubroutineCall) : Term() {
    override fun toString(): String {
        return "SubroutineCallTerm(subroutineCall=$subroutineCall)"
    }
}


val validKeywordTerms = hashSetOf(Keyword.TRUE, Keyword.FALSE, Keyword.NULL, Keyword.THIS)

fun JackDSL.compileTerm(): Term {
    val token = consumeToken()
    val value = token.value
    return when (token) {
        is StringConstantToken -> StringTerm(value)
        is IntegerConstantToken -> IntegerTerm(token.int)
        is KeywordToken -> {
            if (validKeywordTerms.contains(token.keyword)) KeywordTerm(token.keyword)
            else throw Exception("Unexpected keyword ${token.keyword.value}")
        }
        is IdentifierToken -> compileIdentifierToken(value)
        is SymbolToken -> compileSymbolToken(token)
    }
}

private fun JackDSL.compileIdentifierToken(identifier: String): Term {
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

private fun JackDSL.compileSymbolToken(token: SymbolToken): Term {
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
        else -> throw Exception("Expected valid term, but got symbol ${token.value}")
    }
}
