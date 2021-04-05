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
    val code = mutableListOf<String>()

    fun push(variable: VmVariable?) {
        if (variable == null) throw Exception()
        push(variable.stack, variable.index)
    }

    fun push(stack: VmStack, index: Int) {
        code.add("push $stack $index")
    }

    fun pop(stack: VmStack, index: Int) {
        code.add("pop $stack $index")
    }

    fun call(function: String, argCount: Int) {
        code.add("call $function $argCount")
    }

    fun add(op: VmOperator) {
        add(op.value)
    }

    fun add(s: String) {
        code.add(s)
    }

}
