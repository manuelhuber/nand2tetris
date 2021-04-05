package compiler.code

import utils.VmStack


class SymbolTable(val parent: SymbolTable? = null) {

    private val variables = hashMapOf<String, VmVariable>()

    public fun lookup(name: String): VmVariable? {
        var cur: HashMap<String, VmVariable>? = variables
        while (cur != null) {
            if (cur.containsKey(name)) {
                return cur[name]
            }
            cur = parent?.variables
        }
        return null
    }
}

class VmVariable(val type: String, val stack: VmStack, val index: Int)
