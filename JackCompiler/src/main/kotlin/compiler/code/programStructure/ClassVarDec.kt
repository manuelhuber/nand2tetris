package compiler.code.programStructure

import compiler.JackAnalyzerDSL
import utils.Keyword

class ClassVarDec(val fieldType: Keyword, val varDec: VarDec)

fun JackAnalyzerDSL.compileClassVarDec(): ClassVarDec {
    return inTag("classVarDec") {
        val type = consumeKeyword(listOf(Keyword.STATIC, Keyword.FIELD)).keyword
        ClassVarDec(type, compileVarDec())
    }
}
