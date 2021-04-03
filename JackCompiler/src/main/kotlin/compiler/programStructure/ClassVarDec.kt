package compiler.programStructure

import compiler.JackDSL
import compiler.Keyword

class ClassVarDec(val fieldType: Keyword, val varDec: VarDec)

fun JackDSL.compileClassVarDec(): ClassVarDec {
    return inTag("classVarDec") {
        val type = consumeKeyword(listOf(Keyword.STATIC, Keyword.FIELD)).keyword
        ClassVarDec(type, compileVarDec())
    }
}
