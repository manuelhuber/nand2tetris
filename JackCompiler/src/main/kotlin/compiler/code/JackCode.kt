package compiler.code

import utils.VmOperator
import utils.VmStack

abstract class JackCode {
    abstract fun VmDSL.addVmCode(symbols: SymbolTable)

    fun compileToVm(dsl: VmDSL, symbols: SymbolTable) = run { dsl.addVmCode(symbols) }
}

fun tag(tag: String): (String) -> String {
    return { content: String -> "<$tag> $content </$tag>" }
}

class VmDSL {
    var counter = 0

    fun getUniqueNumber(): Int {
        return counter++
    }

    val code = mutableListOf<String>()

    fun push(variable: VmVariable?) {
        if (variable == null) throw Exception()
        push(variable.stack, variable.index)
    }

    fun push(stack: VmStack, index: Int) {
        add("push $stack $index")
    }

    fun pop(variable: VmVariable?) {
        if (variable == null) throw Exception()
        pop(variable.stack, variable.index)
    }

    fun pop(stack: VmStack, index: Int) {
        add("pop $stack $index")
    }

    fun call(function: String, argCount: Int) {
        add("call $function $argCount")
    }

    fun ifGoto(labelName: String) {
        add("if-goto $labelName")
    }

    fun goto(labelName: String) {
        add("goto $labelName")
    }

    fun label(labelName: String) {
        add("label $labelName")
    }

    fun add(op: VmOperator) {
        add(op.value)
    }

    fun add(s: String) {
        code.add(s)
    }

}
