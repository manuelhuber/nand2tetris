package compiler.code.programStructure

import compiler.JackAnalyizerDSL
import utils.Keyword
import utils.Symbol
import compiler.tokenizer.IdentifierToken
import compiler.tokenizer.KeywordToken
import compiler.tokenizer.isA

class SubroutineDec(
    functionType: KeywordToken,
    returnType: String,
    functionName: IdentifierToken,
    params: ParameterList,
    body: SubroutineBody
)

fun JackAnalyizerDSL.compileSubroutineDec(): SubroutineDec {
    return inTag("subroutineDec") {
        val functionType = consumeKeyword(listOf(Keyword.CONSTRUCTOR, Keyword.FUNCTION, Keyword.METHOD))
        val returnType = if (peak().isA(Keyword.VOID)) {
            consumeKeyword(Keyword.VOID).value
        } else {
            compileType()
        }
        val functionName = consumeIdentifier()

        consumeSymbol(Symbol.ROUND_BRACKET_OPEN)
        val params = compileParameterList()
        consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)

        val body = compileSubroutineBody()
        SubroutineDec(functionType, returnType, functionName, params, body)
    }
}
