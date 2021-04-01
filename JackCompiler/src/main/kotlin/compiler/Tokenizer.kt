package compiler

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
                char == SLASH && pos < line.length - 1 && line[pos + 1] == SLASH -> {
                    // remove online comments
                    pos = line.length
                }
                SYMBOLS.contains(char) -> {
                    tokens.add(Symbol(char.toString()))
                    pos++
                }
                char == '"' -> {
                    var end = pos + 1
                    while (line[end] != '"') end++
                    tokens.add(StringConstant(line.substring(pos + 1, end)))
                    pos = end + 1
                }
                char.isDigit() -> {
                    var end = pos + 1
                    while (line[end].isDigit() && end < line.length) end++
                    tokens.add(IntegerConstant(line.substring(pos, end)))
                    pos = end
                }
                else -> {
                    var end = pos + 1
                    fun isWordEnd(i: Int) = i == line.length || SYMBOLS.contains(line[i]) || line[i].isWhitespace()
                    while (!isWordEnd(end)) end++
                    val word = line.substring(pos, end)
                    val token = if (KEYWORDS.contains(word)) Keyword(word) else Identifier(word)
                    tokens.add(token)
                    pos = end
                }
            }
        }
    }
}
