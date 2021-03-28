package assembler

fun instructionToBinary(instruction: String): String {
    return if (instruction.isAInstruction())
        aInstructionToBinary(instruction)
    else
        cInstructionToBinary(instruction)
}

fun aInstructionToBinary(instruction: String): String {
    val address = instruction.substring(1)
    val binary = address.toUInt().toString(radix = 2)
    if (binary.length > 15) throw InvalidSyntax("Number too large")
    return binary.padStart(16, '0')
}

fun cInstructionToBinary(instruction: String): String {
    val (dest, comp, jmp) = parseCInstruction(instruction)
    return "111" + compTable[comp] + destTable[dest] + jmpTable[jmp]
}

fun parseCInstruction(instruction: String): Triple<String, String, String> {
    var dest = ""
    var comp = ""
    var jmp = ""
    val equation =
        if (!instruction.contains(";")) {
            instruction
        } else {
            val split = instruction.split(";")
            jmp = split[1].trim()
            split[0]
        }

    if (!equation.contains("=")) {
        comp = equation.trim()
    } else {
        val split = equation.split("=")
        dest = split[0].trim()
        comp = split[1].trim()
    }

    return Triple(dest, comp, jmp)
}

