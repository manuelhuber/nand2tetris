package compiler

import compiler.tokenizer.KeywordToken
import compiler.tokenizer.SymbolToken
import compiler.tokenizer.Token

class JackDSL(private val tokens: List<Token>) {
    private var index = 0

    public fun consumeSymbol(symbol: Symbol): Token {
        return consumeAnySymbol(listOf(symbol))
    }

    public fun consumeAnySymbol(symbols: Collection<Symbol>): SymbolToken {
        val token = tokens[index]
        if (token !is SymbolToken || symbols.all { symbol -> token.value != symbol.value.toString() }) {
            throw Exception("Expected ${symbols.map(Symbol::value)} but found $token")
        }
        index++
        return token
    }

    public fun consumeKeyword(symbol: Keyword): KeywordToken {
        val token = tokens[index]
        if (token !is KeywordToken || token.value != symbol.value) {
            throw Exception("Expected ${symbol.value} but found $token")
        }
        index++
        return token
    }

    public fun consumeToken(): Token {
        return tokens[index++]
    }

    public fun peak(): Token {
        return tokens[index]
    }

    public fun <T> analyze(x: JackDSL.() -> T): T {
        return this.x()
    }


}

