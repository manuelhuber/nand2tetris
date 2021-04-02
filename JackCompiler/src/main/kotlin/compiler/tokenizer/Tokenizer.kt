package compiler.tokenizer

import compiler.KEYWORDS
import compiler.Keyword
import compiler.SYMBOLS
import compiler.Symbol
import utils.isCode

class Tokenizer {
    private val tokens = mutableListOf<Token>()

    fun tokenize(lines: List<String>): List<Token> {
        tokens.clear()
        lines.filter(String::isCode).forEach(this::tokenizeLine)
        return tokens
    }

    private fun tokenizeLine(line: String) {
        var pos = 0
        while (pos < line.length) {
            val char = line[pos]
            when {
                char.isWhitespace() -> {
                    pos++
                }
                char == Symbol.SLASH.value && pos < line.length - 1 && line[pos + 1] == Symbol.SLASH.value -> {
                    // remove online comments
                    pos = line.length
                }
                SYMBOLS.contains(char) -> {
                    tokens.add(SymbolToken(Symbol.fromValue(char)))
                    pos++
                }
                char == '"' -> {
                    var end = pos + 1
                    while (line[end] != '"') end++
                    tokens.add(StringConstantToken(line.substring(pos + 1, end)))
                    pos = end + 1
                }
                char.isDigit() -> {
                    var end = pos + 1
                    while (line[end].isDigit() && end < line.length) end++
                    tokens.add(IntegerConstantToken(line.substring(pos, end).toInt()))
                    pos = end
                }
                else -> {
                    var end = pos + 1
                    fun isWordEnd(i: Int) = i == line.length || SYMBOLS.contains(line[i]) || line[i].isWhitespace()
                    while (!isWordEnd(end)) end++
                    val word = line.substring(pos, end)
                    val token =
                        if (KEYWORDS.contains(word)) KeywordToken(Keyword.fromValue(word)) else IdentifierToken(word)
                    tokens.add(token)
                    pos = end
                }
            }
        }
    }
}
