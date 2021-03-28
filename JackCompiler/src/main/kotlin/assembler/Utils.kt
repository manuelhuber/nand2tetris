package assembler

fun String.isGotoLabel(): Boolean {
    return this.startsWith("(") && this.endsWith(")")
}

fun String.isAInstruction(): Boolean {
    return this.startsWith("@")
}
