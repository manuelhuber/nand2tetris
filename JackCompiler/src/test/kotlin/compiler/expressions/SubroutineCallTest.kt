package compiler.expressions

import compiler.JackDSL
import org.junit.Test
import testCompilation
import kotlin.test.assertEquals

internal class SubroutineCallTest {
    @Test
    fun testStaticFunction() {
        val subroutineCall = testCompilation("Something.myfun(1+2+3+4,\"sad\",bar())", JackDSL::compileSubroutineCall)
        assertEquals(subroutineCall.functionName, "myfun")
        assertEquals(subroutineCall.targetName, "Something")
        assertEquals(subroutineCall.args.expressions.count(), 3)
    }

    @Test
    fun testNormalFunction() {
        val subroutineCall = testCompilation("foobar()", JackDSL::compileSubroutineCall)
        assertEquals(subroutineCall.functionName, "foobar")
        assertEquals(subroutineCall.targetName, null)
        assertEquals(subroutineCall.args.expressions.count(), 0)
    }
}
