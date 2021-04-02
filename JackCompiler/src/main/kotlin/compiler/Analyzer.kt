package compiler

import compiler.models.*
import compiler.tokenizer.*


class Analyzer(private val tokens: List<Token>) {
    private val jack: JackDSL = JackDSL(tokens)
    fun analyze(tokens: List<Token>) {

    }

    fun compileExpression(): Expression {
        return jack.analyze {

            val firstTerm = compileTerm()
            val additionalTerms = mutableListOf<Pair<Operator, Term>>()
            while (peak() is SymbolToken && OPERATORS.contains(peak().value[0])) {
                val symbolToken = consumeAnySymbol(Operator.values().map { operator -> operator.value })
                val term = compileTerm()
                additionalTerms.add(Pair(Operator.fromValue(symbolToken.symbol), term))
            }
            Expression(firstTerm, additionalTerms)
        }
    }

    fun compileTerm(): Term {
        return jack.analyze {
            val token = consumeToken()
            val value = token.value
            when (token) {
                is StringConstantToken -> StringTerm(value)
                is IntegerConstantToken -> IntegerTerm(token.int)
                is KeywordToken -> KeywordTerm(token.keyword)
                is IdentifierToken -> {
                    val nextToken = peak()
                    val basicVarNameTerm = VarNameTerm(value)
                    if (nextToken !is SymbolToken) {
                        basicVarNameTerm
                    } else when (nextToken.symbol) {
                        Symbol.SQUARE_BRACKET_OPEN -> {
                            consumeSymbol(Symbol.SQUARE_BRACKET_OPEN)
                            val expression = compileExpression()
                            consumeSymbol(Symbol.SQUARE_BRACKET_CLOSE)
                            ArrayVarNameTerm(value, expression)
                        }
                        Symbol.DOT -> {
                            // TODO function call
                            VarNameTerm("")
                        }
                        Symbol.ROUND_BRACKET_OPEN -> {
                            // TODO function call
                            VarNameTerm("")
                        }
                        else -> basicVarNameTerm
                    }
                }
                is SymbolToken -> {
                    when {
                        token.symbol == Symbol.ROUND_BRACKET_OPEN -> {
                            val expression = compileExpression()
                            consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)
                            ExpressionTerm(expression)
                        }
                        UNARY_OPERATORS.contains(token.symbol) -> {
                            val term = compileTerm()
                            UnaryTerm(UnaryOperator.fromValue(token.symbol), term)
                        }
                        else -> throw Exception("Expected valid term, but got symbol $value")
                    }
                }
                else -> throw Exception("Expected valid term, but got $value")
            }
        }
    }

    fun compileWhileStatement(): WhileStatement {
        return jack.analyze {
            consumeKeyword(Keyword.WHILE)
            consumeSymbol(Symbol.ROUND_BRACKET_OPEN)
            val expression = compileExpression()
            consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)
            consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
            val statements = compileStatements()
            consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)

            return@analyze WhileStatement(expression, statements)
        }
    }

    private fun compileStatements(): Statements {
        TODO("Not yet implemented")
    }
}
