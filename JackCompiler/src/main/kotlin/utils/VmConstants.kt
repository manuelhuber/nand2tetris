package utils

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

enum class VmOperator(val value: String) {
    Push("push"),
    Pop("pop"),
    Add("add"),
    Sub("sub"),
    And("and"),
    Or("or"),
    Neg("neg"),
    Not("not"),
    Gt("gt"),
    Lt("lt"),
    Eq("eq"),
    Label("label"),
    Goto("goto"),
    IfGoto("if-goto"),
    Function("function"),
    Call("call"),
    Return("return");

    override fun toString(): String {
        return value
    }

}


enum class VmStack(val VmValue: String, val assemblyValue: String?) {
    LOCAL("local", Local),
    ARGUMENT("argument", Argument),
    THIS("this", This),
    THAT("that", That),
    CONSTANT("constant", null),
    STATIC("static", null),
    POINTER("pointer", null),
    TEMP("temp", null);


    companion object {
        fun fromValue(keyword: String) = values().firstOrNull { symbol -> symbol.VmValue == keyword }
    }

    override fun toString(): String {
        return VmValue
    }
}
