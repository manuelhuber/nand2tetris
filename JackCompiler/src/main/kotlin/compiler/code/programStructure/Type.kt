package compiler.code.programStructure

import compiler.CompilationError
import compiler.JackAnalyzerDSL
import utils.Keyword
import compiler.tokenizer.IdentifierToken
import compiler.tokenizer.KeywordToken

fun JackAnalyzerDSL.compileType(): String {
    return when (val typeToken = consumeToken()) {
        is IdentifierToken -> typeToken.value
        is KeywordToken -> {
            when (typeToken.keyword) {
                Keyword.INT -> Keyword.INT.value
                Keyword.CHAR -> Keyword.CHAR.value
                Keyword.BOOLEAN -> Keyword.BOOLEAN.value
                else -> throw CompilationError("Not a valid type. Unexpected keyword ${typeToken.keyword.value}")
            }
        }
        else -> throw CompilationError("Not a valid type. Unexpected token $typeToken")
    }
}
