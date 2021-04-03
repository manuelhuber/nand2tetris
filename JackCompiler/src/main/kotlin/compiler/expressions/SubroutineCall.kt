package compiler.expressions

import compiler.CompilationError
import compiler.JackDSL
import compiler.Symbol
import compiler.tokenizer.SymbolToken

class SubroutineCall(val functionName: String, val args: ExpressionList, val targetName: String? = null) {
    override fun toString(): String {
        return "SubroutineCall(functionName='$functionName', args=$args, targetName=$targetName)"
    }
}

fun JackDSL.compileSubroutineCall(): SubroutineCall {
    val identifier = consumeIdentifier()
    val symbol = peak()
    if (symbol !is SymbolToken) {
        throw CompilationError("Unexpected token ${symbol.value}")
    }
    return when (symbol.symbol) {
        Symbol.DOT -> {
            // MyClass.myFunction(arg1, arg2)
            consumeSymbol(Symbol.DOT)
            val functionName = consumeIdentifier()
            val args = compileArgsList()
            SubroutineCall(functionName.value, args, identifier.value)
        }
        Symbol.ROUND_BRACKET_OPEN -> {
            // myFunction(arg1, arg2)
            val args = compileArgsList()
            SubroutineCall(identifier.value, args)
        }
        else -> throw CompilationError("Unexpected symbol '${symbol.symbol.value}' after identifier")
    }
}

fun JackDSL.compileArgsList(): ExpressionList {
    consumeSymbol(Symbol.ROUND_BRACKET_OPEN)
    val peak = peak()
    val expressionList =
        if (peak !is SymbolToken || peak.symbol != Symbol.ROUND_BRACKET_CLOSE) {
            compileExpressionList()
        } else {
            ExpressionList()
        }
    consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)
    return expressionList
}
