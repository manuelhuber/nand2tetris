package compiler.tokenizer

import utils.Keyword
import utils.Symbol

sealed class Token(val value: String) {

    override fun toString(): String {
        val simpleName = this::javaClass.get().simpleName.decapitalize().removeSuffix("Token")
        return "<$simpleName> $value </$simpleName>"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Token

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

class KeywordToken(val keyword: Keyword) : Token(keyword.value)
class SymbolToken(val symbol: Symbol) : Token(symbol.value.toString())
class IntegerConstantToken(val int: Int) : Token(int.toString())
class StringConstantToken(value: String) : Token(value)
class IdentifierToken(value: String) : Token(value)

fun Token.isA(keyword: Keyword): Boolean {
    return this is KeywordToken && this.keyword == keyword
}

fun Token.isA(symbol: Symbol): Boolean {
    return this is SymbolToken && this.symbol == symbol
}
