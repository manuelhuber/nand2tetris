package vmTranslator


class AssemblyDsl {
    val output = mutableListOf<String>()
    private val tempStackBase = 5

    fun addCode(statements: AssemblyDsl.() -> Unit) {
        this.statements()
    }

    fun addComment(string: String) {
        output.add("// $string")
    }

    fun addressTmp(num: Int) {
        output.add("@${tempStackBase + num}")
    }

    fun address(string: String) {
        output.add("@$string")
    }

    fun addressPointer(string: String) {
        address(string)
        setAddress(Memory)
    }

    fun setAddress(value: String) {
        output.add("$Address=$value")
    }

    fun setData(value: String) {
        output.add("$Data=$value")
    }

    fun setMemory(value: String) {
        output.add("$Memory=$value")
    }

    fun incrementStackPointer() {
        address(StackPointer)
        setMemory("$Memory+1")
    }

    fun decrementStackPointer() {
        address(StackPointer)
        setMemory("$Memory-1")
    }

    fun addLabel(label: String) {
        output.add("($label)")
    }

    fun jump(value: String, jumpCommand: Jump) {
        output.add("$value;${jumpCommand.key}")
    }

    fun jump() {
        jump("0", Jump.Always)
    }
}


