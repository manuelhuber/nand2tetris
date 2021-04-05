package vmTranslator

import utils.*
import utils.VmOperator.*
import utils.VmStack.*
import utils.isCode

class VmTranslator() {
    private var staticIdentifier: String = "MAIN"
    private var assembly = AssemblyDsl()
    private var counter = 0
    private val savedFrame = listOf(Local, Argument, This, That)

    constructor(bootstrap: Boolean) : this() {
        if (bootstrap)
            assembly.addCode {
                address("256")
                setData(Address)
                address(StackPointer)
                setMemory(Data)
                translateFunctionCall(listOf("call", "Sys.init", "0"))
            }
    }

    private fun frameSize(): Int {
        // +1 for the return address
        return savedFrame.count() + 1
    }

    private fun getCounterAndIncrement(): Int {
        return counter++
    }

    fun getFullCode(): MutableList<String> {
        return assembly.output
    }

    fun translate(code: List<String>, staticIdentifier: String): List<String> {
        this.staticIdentifier = staticIdentifier
        code.filter(String::isCode).forEach(this::translateLine)
        return assembly.output
    }

    private fun translateLine(line: String) {
        assembly.addComment(line)
        val tokens = line.split(' ')
        val operation = tokens[0]
        when (operation) {
            Push.value -> translatePush(tokens)
            Pop.value -> translatePop(tokens)
            Add.value -> translateBasicTwoTermOperation("+")
            Sub.value -> translateBasicTwoTermOperation("-")
            And.value -> translateBasicTwoTermOperation("&")
            Or.value -> translateBasicTwoTermOperation("|")
            Neg.value -> translateBasicSingleTermOperation("-")
            Not.value -> translateBasicSingleTermOperation("!")
            Gt.value -> translateComparison(Jump.IfGreater)
            Lt.value -> translateComparison(Jump.IfLess)
            Eq.value -> translateComparison(Jump.IfEqual)
            Label.value -> translateLabel(tokens)
            Goto.value -> translateGoto(tokens)
            IfGoto.value -> translateConditionalGoto(tokens)
            VmOperator.Function.value -> translateFunctionDeclaration(tokens)
            Call.value -> translateFunctionCall(tokens)
            Return.value -> translateReturn()
        }
    }

    private fun translatePush(tokens: List<String>) {
        val stack = tokens[1]
        val number = tokens[2]
        assembly.addCode {
            // Set data
            when (stack) {
                CONSTANT.VmValue -> {
                    address(number)
                    setData(Address)
                }
                STATIC.VmValue -> {
                    address("$staticIdentifier.$number")
                    setData(Memory)
                }
                POINTER.VmValue -> {
                    address(if (number == "0") This else That)
                    setData(Memory)
                }
                TEMP.VmValue -> {
                    addressTmp(number.toInt())
                    setData(Memory)
                }
                else -> {
                    val base = VmStack.fromValue(stack)!!.assemblyValue!!
                    address(number)
                    setData(Address)
                    address(base)
                    setAddress("$Data+$Memory")
                    setData(Memory)
                }
            }
            // push data to stack
            addressPointer(StackPointer)
            setMemory(Data)
            incrementStackPointer()
        }
    }

    private fun translatePop(tokens: List<String>) {
        val stack = tokens[1]
        val number = tokens[2]

        val baseAddress = VmStack.fromValue(stack)?.assemblyValue
        if (baseAddress != null) {
            return popToGenericLocation(baseAddress, number)
        }

        assembly.addCode {
            decrementStackPointer()
            addressPointer(StackPointer)
            setData(Memory)
            when (stack) {
                STATIC.VmValue -> {
                    address("$staticIdentifier.$number")
                    setMemory(Data)
                }
                TEMP.VmValue -> {
                    addressTmp(number.toInt())
                    setMemory(Data)
                }
                POINTER.VmValue -> {
                    address(if (number == "0") This else That)
                    setMemory(Data)
                }
            }
        }

    }

    private fun popToGenericLocation(base: String, number: String) {
        assembly.addCode {
            // 1. Calculate target address and save it in R13
            address(number)
            setData(Address)
            address(base)
            setData("$Data+$Memory") // this is the target address
            address(R13) // general purpose register
            setMemory(Data)

            // 2. Store stack value in data
            decrementStackPointer()
            addressPointer(StackPointer)
            setData(Memory)

            // 3. Push data to target address
            addressPointer(R13)
            setMemory(Data)
        }
    }

    private fun translateBasicTwoTermOperation(operator: String) {
        assembly.addCode {
            decrementStackPointer()
            addressPointer(StackPointer)
            setData(Memory)

            decrementStackPointer()
            addressPointer(StackPointer)
            setMemory("$Memory$operator$Data")

            incrementStackPointer()
        }
    }

    private fun translateBasicSingleTermOperation(operator: String) {
        assembly.addCode {
            decrementStackPointer()
            addressPointer(StackPointer)
            setMemory("$operator$Memory")
            incrementStackPointer()
        }
    }

    private fun translateComparison(jumpCondition: Jump) {
        val trueLabel = "true${getCounterAndIncrement()}"
        val continueLabel = "continue${getCounterAndIncrement()}"
        assembly.addCode {
            decrementStackPointer() // at y
            addressPointer(StackPointer)
            setData(Memory)
            decrementStackPointer()
            addressPointer(StackPointer) // at x
            setData("$Memory-$Data") // d = x-y

            address(trueLabel)
            jump(Data, jumpCondition)

            // it's false
            addressPointer(StackPointer)
            setMemory("0") // FALSE
            address(continueLabel)
            jump()

            // it's true
            addLabel(trueLabel)
            addressPointer(StackPointer)
            setMemory("-1") // TRUE

            // done
            addLabel(continueLabel)
            incrementStackPointer()
        }
    }

    private fun translateLabel(tokens: List<String>) {
        val labelName = tokens[1]
        assembly.addCode {
            addLabel(labelName)
        }
    }

    private fun translateGoto(tokens: List<String>) {
        val label = tokens[1]
        assembly.addCode {
            address(label)
            jump()
        }
    }

    private fun translateConditionalGoto(tokens: List<String>) {
        val label = tokens[1]
        assembly.addCode {
            decrementStackPointer()
            addressPointer(StackPointer)
            setData(Memory)
            address(label)
            jump(Data, Jump.IfNotEqual)
        }
    }

    private fun translateFunctionDeclaration(tokens: List<String>) {
        val functionName = tokens[1]
        val localStackSize = tokens[2].toInt()
        assembly.addCode {
            addLabel(functionName)
            for (i in 1..localStackSize) {
                addressPointer(StackPointer)
                setMemory("0")
                incrementStackPointer()
            }
        }
    }

    private fun translateFunctionCall(tokens: List<String>) {
        val functionName = tokens[1]
        val argCount = tokens[2].toInt()
        val returnLabel = "$staticIdentifier.$functionName\$ret.${getCounterAndIncrement()}"
        assembly.addCode {

            // push return label
            address(returnLabel)
            setData(Address)
            addressPointer(StackPointer)
            setMemory(Data)
            incrementStackPointer()

            // save caller's frame
            for (pointer in savedFrame) {
                address(pointer)
                setData(Memory)
                addressPointer(StackPointer)
                setMemory(Data)
                incrementStackPointer()
            }

            // Set arg to the position
            // ARG = SP - argCount - frameSize
            address(argCount.toString())
            setData(Address)
            address(frameSize().toString())
            setData("$Data+$Address")
            address(StackPointer)
            setData("$Memory-$Data")
            address(Argument)
            setMemory(Data)

            // LCL = SP
            copyMemory(StackPointer, Local)

            address(functionName)
            jump()

            addLabel(returnLabel)
        }
    }

    private fun translateReturn() {
        assembly.addCode {

            // R13 = lcl
            // We will count down R13 to go through the saved frame
            copyMemory(Local, R13)

            // save returnAddress in R14
            address(Local)
            setData(Memory)
            address(frameSize().toString())
            // A = *LCL - frameSize = location where return address is saved
            setAddress("$Data-$Address")
            setData(Memory)
            address(R14)
            setMemory(Data)

            // *ARG = pop()
            decrementStackPointer()
            addressPointer(StackPointer)
            setData(Memory)
            addressPointer(Argument)
            setMemory(Data)

            // SP = ARG + 1
            address(Argument)
            setData("$Memory+1")
            address(StackPointer)
            setMemory(Data)

            for (pointer in savedFrame.reversed()) {
                address(R13)
                setMemory("$Memory-1")
                setAddress(Memory)
                setData(Memory)
                address(pointer)
                setMemory(Data)
            }

            // jump to return address
            addressPointer(R14)
            jump()
        }
    }

}
