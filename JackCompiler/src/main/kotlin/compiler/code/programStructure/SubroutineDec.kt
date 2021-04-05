package compiler.code.programStructure

import compiler.JackAnalyzerDSL
import compiler.code.JackCode
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.VmVariable
import compiler.tokenizer.IdentifierToken
import compiler.tokenizer.isA
import utils.Keyword
import utils.Symbol
import utils.VmOperator
import utils.VmStack

class SubroutineDec(
    val functionType: Keyword,
    val returnType: String,
    val functionName: IdentifierToken,
    val params: ParameterList,
    val body: SubroutineBody
) : JackCode() {

    private lateinit var className: String
    private var constuctorSize: Int = 0

    private fun isMethod() = functionType == Keyword.METHOD
    fun isConstructor() = functionType == Keyword.CONSTRUCTOR
    fun setConstructorSize(size: Int) {
        constuctorSize = size
    }

    fun setClassName(s: String) {
        this.className = s
    }

    override fun VmDSL.addVmCode(symbols: SymbolTable) {
        initializeSymbols(symbols)

        val localVarCount = body.varDecs.flatMap(VarDec::names).count()
        add("${VmOperator.Function} $className.${functionName.value} $localVarCount")

        if (isMethod()) {
            // set THIS
            push(VmStack.ARGUMENT, 0)
            pop(VmStack.POINTER, 0)
        }

        if (isConstructor()) {
            push(VmStack.CONSTANT, constuctorSize)
            call("Memory.alloc", 1)
            pop(VmStack.POINTER, 0)
        }

        body.compileToVm(this, symbols)
    }

    private fun initializeSymbols(symbols: SymbolTable) {
        // ARGS
        var argsIndex = 0
        if (isMethod()) {
            argsIndex++ // args 0 is THIS
        }
        params.pars.forEach { parameter ->
            symbols.add(parameter.identifier, VmVariable(parameter.type, VmStack.ARGUMENT, argsIndex++))
        }

        // LOCAL
        var localVarIndex = 0
        body.varDecs.forEach { varDec ->
            varDec.names.forEach {
                symbols.add(it, VmVariable(varDec.type, VmStack.LOCAL, localVarIndex++))
            }
        }
    }

}

fun JackAnalyzerDSL.compileSubroutineDec(): SubroutineDec {
    return inTag("subroutineDec") {
        val functionType = consumeKeyword(listOf(Keyword.CONSTRUCTOR, Keyword.FUNCTION, Keyword.METHOD))
        val returnType = if (peak().isA(Keyword.VOID)) {
            consumeKeyword(Keyword.VOID).value
        } else {
            compileType()
        }
        val functionName = consumeIdentifier()

        consumeSymbol(Symbol.ROUND_BRACKET_OPEN)
        val params = compileParameterList()
        consumeSymbol(Symbol.ROUND_BRACKET_CLOSE)

        val body = compileSubroutineBody()
        SubroutineDec(functionType.keyword, returnType, functionName, params, body)
    }
}
