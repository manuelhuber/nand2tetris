package compiler.code.programStructure

import compiler.JackAnalyizerDSL
import utils.Keyword

class ClassVarDec(val fieldType: Keyword, val varDec: VarDec)

fun JackAnalyizerDSL.compileClassVarDec(): ClassVarDec {
    return inTag("classVarDec") {
        val type = consumeKeyword(listOf(Keyword.STATIC, Keyword.FIELD)).keyword
        ClassVarDec(type, compileVarDec())
    }
}
