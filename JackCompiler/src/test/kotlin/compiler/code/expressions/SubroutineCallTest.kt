package compiler.code.expressions

import compiler.JackAnalyizerDSL
import org.junit.Test
import testCompilation
import kotlin.test.assertEquals

internal class SubroutineCallTest {
    @Test
    fun testStaticFunction() {
        val subroutineCall = testCompilation("Something.myfun(1+2+3+4,\"sad\",bar())", JackAnalyizerDSL::compileSubroutineCall)
        assertEquals(subroutineCall.functionName, "myfun")
        assertEquals(subroutineCall.targetName, "Something")
        assertEquals(subroutineCall.args.expressions.count(), 3)
    }

    @Test
    fun testNormalFunction() {
        val subroutineCall = testCompilation("foobar()", JackAnalyizerDSL::compileSubroutineCall)
        assertEquals(subroutineCall.functionName, "foobar")
        assertEquals(subroutineCall.targetName, null)
        assertEquals(subroutineCall.args.expressions.count(), 0)
    }
}
