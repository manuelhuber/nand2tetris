package vmTranslator

enum class Jump(val key: String) {
    IfGreater("JGT"),
    IfEqual("JEQ"),
    IfGreaterOrEqual("JGE"),
    IfLess("JLT"),
    IfNotEqual("JNE"),
    IfLessOrEqual("JLE"),
    Always("JMP"),
}
