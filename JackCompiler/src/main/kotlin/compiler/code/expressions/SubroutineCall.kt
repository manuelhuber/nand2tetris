package compiler.code.expressions

import compiler.CompilationError
import compiler.JackAnalyzerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.tokenizer.SymbolToken
import utils.Symbol

class SubroutineCall(val functionName: String, val args: ExpressionList, val targetName: String? = null) : JackCode() {
    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        val isMethodCall = targetName != null && targetName[0].isLowerCase()
        if (isMethodCall) {
            push(symbols.lookup(targetName!!))
        }
        args.compileToVm(this, symbols)
        var name = if (targetName == null) functionName else "$targetName.$functionName"
        call(name, args.count() + if (isMethodCall) 1 else 0)
    }
}

fun JackAnalyzerDSL.compileSubroutineCall(): SubroutineCall {
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

fun JackAnalyzerDSL.compileArgsList(): ExpressionList {
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
