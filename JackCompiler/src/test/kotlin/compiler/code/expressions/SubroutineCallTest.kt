package compiler.code.expressions

import compiler.JackAnalyzerDSL
import compiler.code.SymbolTable
import compiler.code.VmDSL
import compiler.code.VmVariable
import org.junit.Test
import testCompilation
import utils.Operator
import utils.VmStack
import kotlin.test.assertEquals

internal class SubroutineCallTest {
    @Test
    fun testStaticFunction() {
        val subroutineCall =
            testCompilation("Something.myfun(1+2+3+4,\"sad\",bar())", JackAnalyzerDSL::compileSubroutineCall)
        assertEquals(subroutineCall.functionName, "myfun")
        assertEquals(subroutineCall.targetName, "Something")
        assertEquals(subroutineCall.args.count(), 3)
    }

    @Test
    fun testNormalFunction() {
        val subroutineCall = testCompilation("foobar()", JackAnalyzerDSL::compileSubroutineCall)
        assertEquals(subroutineCall.functionName, "foobar")
        assertEquals(subroutineCall.targetName, null)
        assertEquals(subroutineCall.args.count(), 0)
    }

    @Test
    fun testVmCodeGeneration() {
        val args = listOf(Expression(IntegerTerm(5), listOf(Pair(Operator.PLUS, IntegerTerm(1)))))
        val subroutineCall = SubroutineCall("foo", ExpressionList(args), "myTarget")
        val dsl = VmDSL()
        val classSymbols = SymbolTable()
        classSymbols.add("myTarget", VmVariable("", VmStack.LOCAL, 4))

        subroutineCall.compileToVm(dsl, classSymbols)
        assertEquals(
            dsl.code,
            listOf(
                "push local 4",
                "push constant 5",
                "push constant 1",
                "add",
                "call foo 2",
            )
        )
    }
}
