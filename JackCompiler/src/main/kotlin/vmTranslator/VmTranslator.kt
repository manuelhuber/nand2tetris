package vmTranslator

import assembler.isCode

class VmTranslator {
    private lateinit var staticIdentifier: String
    private var assembly = AssemblyDsl()
    private var counter = 0;

    private fun getCounterAndIncrement(): Int {
        return counter++;
    }

    fun translate(code: List<String>, staticIdentifier: String): List<String> {
        this.staticIdentifier = staticIdentifier
        code.filter { line -> line.isCode() }.forEach(this::translateLine)
        return assembly.output
    }

    fun translateLine(line: String) {
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
            "gt" -> translateCompareTwoOperators(Jump.IfGreater)
            "lt" -> translateCompareTwoOperators(Jump.IfLess)
            "eq" -> translateCompareTwoOperators(Jump.IfEqual)
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
                else -> {
                    val base = stackTable[stack]

                    // 1. Get target address and save it in R13
                    // e.g. pop local 3
                    address(number) // a = 3
                    setData(Address) // d = a
                    address(base!!) // a = 1
                    setData("$Data+$Memory") // d = 3+RAM[1] // this is the target address
                    addressPointer(StackPointer) // a = RAM[a]
                    setAddress("13") // general purpose register
                    setMemory(Data)

                    // Store value in data
                    addressPointer(StackPointer)
                    setData(Memory)

                    // Push data to target address
                    addressPointer(StackPointer)
                    setAddress("13")
                    setAddress(Memory)
                    setMemory(Data)
                }
            }


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

    private fun translateCompareTwoOperators(jumpCondition: Jump) {
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
