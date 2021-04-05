package compiler.code

import utils.VmStack

abstract class JackCode {
    abstract fun VmDSL.toVmCode(symbols: SymbolTable)

    fun toVmCodeX(dsl: VmDSL, symbols: SymbolTable) {
        this.run { dsl.toVmCode(symbols) }
    }
}

fun tag(tag: String): (String) -> String {
    return { content: String -> "<$tag> $content </$tag>" }
}

class VmDSL() {
    private val code = mutableListOf<String>()

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

    fun add(s: String) {
        code.add(s)
    }

}
