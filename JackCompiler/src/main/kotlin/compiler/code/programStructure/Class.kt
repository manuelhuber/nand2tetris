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

class Class(val name: String, val vars: MutableList<VarDec>, val subroutines: List<SubroutineDec>) : JackCode() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        // Create symbol table for class
        var varCount = 0
        vars.forEach { classVarDec ->
            val stack = if (classVarDec.fieldType == Keyword.STATIC) VmStack.STATIC else VmStack.THIS
            classVarDec.names.forEach {
                val vmVariable = VmVariable(classVarDec.type, stack, varCount++)
                symbols.add(it, vmVariable)
            }
        }

        subroutines.forEach { subroutineDec ->
            subroutineDec.setClassName(name)
            if (subroutineDec.isConstructor()) {
                subroutineDec.setConstructorSize(varCount)
            }
            val subroutineScopeSymbols = SymbolTable(symbols)
            subroutineScopeSymbols.add(Keyword.THIS.value, VmVariable(name, VmStack.POINTER, 0))
            subroutineDec.compileToVm(this, subroutineScopeSymbols)
        }

    }
}

fun JackAnalyzerDSL.compileClass(): Class {
    return inTag("class") {
        consumeKeyword(Keyword.CLASS)
        val name = consumeIdentifier().value
        consumeSymbol(Symbol.CURLY_BRACKET_OPEN)
        val vars = mutableListOf<VarDec>()
        while (peak().isA(Keyword.STATIC) || peak().isA(Keyword.FIELD)) {
            vars.add(compileVarDec())
        }
        val subroutines = mutableListOf<SubroutineDec>()
        while (!peak().isA(Symbol.CURLY_BRACKET_CLOSE)) {
            subroutines.add(compileSubroutineDec())
        }
        consumeSymbol(Symbol.CURLY_BRACKET_CLOSE)
        Class(name, vars, subroutines)
    }
}
