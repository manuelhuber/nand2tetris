package assembler

import utils.removeComments

class Assembler {
    private var symbols: HashMap<String, Int> = HashMap();

    fun assemble(code: List<String>): List<String> {
        symbols = getSymbols()
        var cleanedCode = removeComments(code)
        cleanedCode = removeGotoSymbols(cleanedCode)
        cleanedCode = replaceASymbols(cleanedCode)
        return convertToBinary(cleanedCode)
    }

    private fun removeGotoSymbols(code: List<String>): List<String> {
        var lineOfCode = 0
        return code.filter { line ->
            if (line.isGotoLabel()) {
                val symbol = line.substring(1, line.length - 1)
                if (symbols.containsKey(symbol)) throw InvalidSyntax("Duplicate symbol:$symbol")
                symbols[symbol] = lineOfCode
                return@filter false
            } else {
                lineOfCode++
                return@filter true
            }
        }
    }

    private fun replaceASymbols(code: List<String>): List<String> {
        var nextVariableAddress = 16

        fun replaceASymbol(line: String): String {
            val symbol = line.substring(1)
            val value = if (symbol.toIntOrNull() != null) symbol else {
                if (!symbols.containsKey(symbol)) {
                    symbols[symbol] = nextVariableAddress++
                }
                symbols[symbol]
            }
            return "@${value}"
        }

        return code.map { line ->
            if (line.isAInstruction()) replaceASymbol(line) else line
        }
    }

    private fun convertToBinary(code: List<String>): List<String> {
        return code.map(::instructionToBinary)
    }

}
