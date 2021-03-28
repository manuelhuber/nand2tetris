package vmTranslator

const val TempStackBaseAddress = 5

const val Address = "A"
const val Memory = "M"
const val Data = "D"

const val StackPointer = "SP"
const val Local = "LCL"
const val Argument = "ARG"
const val This = "THIS"
const val That = "THAT"

// general purpose register
const val R13 = "13"
const val R14 = "14"
const val R15 = "15"

val stackTable = hashMapOf(
    "local" to Local,
    "argument" to Argument,
    "this" to This,
    "that" to That,
)

