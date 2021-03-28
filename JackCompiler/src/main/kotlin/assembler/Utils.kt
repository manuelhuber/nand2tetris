package assembler

fun String.isCode(): Boolean {
    return this.isNotEmpty() && !this.startsWith("//")
}

fun String.isGotoLabel(): Boolean {
    return this.startsWith("(") && this.endsWith(")")
}

fun String.isAInstruction(): Boolean {
    return this.startsWith("@")
}
