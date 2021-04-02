package compiler

import compiler.tokenizer.IdentifierToken
import compiler.tokenizer.KeywordToken
import compiler.tokenizer.SymbolToken
import compiler.tokenizer.Token

class JackDSL(private val tokens: List<Token>) {
    private var index = 0

    fun consumeSymbol(expected: Symbol): SymbolToken {
        return consumeSymbol(listOf(expected))
    }

    fun consumeSymbol(): SymbolToken {
        return consumeSymbol(listOf())
    }

    fun consumeSymbol(acceptableSymbols: Collection<Symbol>): SymbolToken {
        val token = tokens[index]
        if (token is SymbolToken && (acceptableSymbols.isEmpty() || acceptableSymbols.contains(token.symbol))) {
            index++
            return token
        }
        throw Exception("Expected ${acceptableSymbols.map(Symbol::value)} but found $token")
    }

    fun consumeKeyword(symbol: Keyword): KeywordToken {
        val token = tokens[index]
        if (token !is KeywordToken || token.value != symbol.value) {
            throw Exception("Expected ${symbol.value} but found $token")
        }
        index++
        return token
    }

    fun consumeIdentifier(): IdentifierToken {
        val token = tokens[index]
        if (token !is IdentifierToken) {
            throw Exception("Expected Identifier but found $token")
        }
        index++
        return token
    }

    fun consumeToken(): Token {
        return tokens[index++]
    }

    fun peak(): Token {
        return tokens[index]
    }

    fun backpaddle() {
        index--
    }

    fun <T> analyze(x: JackDSL.() -> T): T {
        return this.x()
    }


}

