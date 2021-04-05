package compiler.code.programStructure

import compiler.JackAnalyzerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.VmVariable
import compiler.tokenizer.isA
import utils.Keyword
import utils.Symbol
import utils.VmStack

class Class(val name: String, val vars: List<ClassVarDec>, val subroutines: List<SubroutineDec>) : JackCode() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        // Create symbol table for class
        var varCount = 0
        vars.forEach { classVarDec ->
            val stack = if (classVarDec.fieldType == Keyword.STATIC) VmStack.STATIC else VmStack.THIS
            classVarDec.varDec.names.forEach {
                val vmVariable = VmVariable(classVarDec.varDec.type, stack, varCount++)
                symbols.add(it, vmVariable)
            }
        }

        subroutines.forEach { subroutineDec ->
            subroutineDec.setClassName(name)
            val subroutineScopeSymbols = SymbolTable(symbols)
            subroutineDec.compileToVm(this, subroutineScopeSymbols)
        }

    }
}

fun JackAnalyzerDSL.compileClass(): Class {
    return inTag("class") {
        consumeKeyword(Keyword.CLASS)
        val name = consumeIdentifier().value
        consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
        val vars = mutableListOf<ClassVarDec>()
        while (peak().isA(Keyword.STATIC) || peak().isA(Keyword.FIELD)) {
            vars.add(compileClassVarDec())
        }
        val subroutines = mutableListOf<SubroutineDec>()
        while (!peak().isA(Symbol.CURLY_BRACKET_CLOSE)) {
            subroutines.add(compileSubroutineDec())
        }
        consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)
        Class(name, vars, subroutines)
    }
}
