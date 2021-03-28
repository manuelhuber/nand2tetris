package vmTranslator

import utils.isCode

class VmTranslator {
    private lateinit var staticIdentifier: String
    private var assembly = AssemblyDsl()
    private var counter = 0;

    private fun getCounterAndIncrement(): Int {
        return counter++;
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
            "push" -> translatePush(tokens)
            "pop" -> translatePop(tokens)
            "add" -> translateBasicTwoTermOperation("+")
            "sub" -> translateBasicTwoTermOperation("-")
            "and" -> translateBasicTwoTermOperation("&")
            "or" -> translateBasicTwoTermOperation("|")
            "neg" -> translateBasicSingleTermOperation("-")
            "not" -> translateBasicSingleTermOperation("!")
            "gt" -> translateComparison(Jump.IfGreater)
            "lt" -> translateComparison(Jump.IfLess)
            "eq" -> translateComparison(Jump.IfEqual)
        }
    }

    private fun translatePush(tokens: List<String>) {
        val stack = tokens[1]
        val number = tokens[2]
        assembly.addCode {
            // Set data
            when (stack) {
                "constant" -> {
                    address(number)
                    setData(Address)
                }
                "static" -> {
                    address("$staticIdentifier.$number")
                    setData(Memory)
                }
                "pointer" -> {
                    address(if (number == "0") This else That)
                    setData(Memory)
                }
                "temp" -> {
                    addressTmp(number.toInt())
                    setData(Memory)
                }
                else -> {
                    val base = stackTable[stack]
                    address(number)
                    setData(Address)
                    address(base!!)
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

        val baseAddress = stackTable[stack]
        if (baseAddress != null) {
            return popToGenericLocation(baseAddress, number)
        }

        assembly.addCode {
            decrementStackPointer()
            addressPointer(StackPointer)
            setData(Memory)
            when (stack) {
                "static" -> {
                    address("$staticIdentifier.$number")
                    setMemory(Data)
                }
                "temp" -> {
                    addressTmp(number.toInt())
                    setMemory(Data)
                }
                "pointer" -> {
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
            address("13") // general purpose register
            setMemory(Data)

            // 2. Store stack value in data
            decrementStackPointer()
            addressPointer(StackPointer)
            setData(Memory)

            // 3. Push data to target address
            addressPointer("13")
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
            setMemory("0")
            address(continueLabel)
            jump()

            // it's true
            addLabel(trueLabel)
            addressPointer(StackPointer)
            setMemory("-1")

            // done
            addLabel(continueLabel)
            incrementStackPointer()
        }
    }

}
