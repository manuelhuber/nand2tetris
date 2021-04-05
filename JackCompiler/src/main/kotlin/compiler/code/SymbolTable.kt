package compiler.code

import utils.VmStack


class SymbolTable(val parent: SymbolTable? = null) {

    private val variables = hashMapOf<String, VmVariable>()

    fun add(name: String, variable: VmVariable) {
        variables[name] = variable
    }

    fun lookup(name: String): VmVariable? {
        var cur: SymbolTable? = this
        while (cur != null) {
            if (cur.variables.containsKey(name)) {
                return cur.variables[name]
            }
            cur = cur.parent
        }
        return null
    }
}

class VmVariable(val type: String, val stack: VmStack, val index: Int)
