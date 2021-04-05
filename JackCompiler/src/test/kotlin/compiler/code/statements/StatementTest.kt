package compiler.code.statements

import compiler.JackAnalyizerDSL
import utils.Operator
import compiler.code.expressions.IntegerTerm
import compiler.code.expressions.VarNameTerm
import org.junit.Test
import testCompilation
import kotlin.test.assertEquals

internal class StatementTest {
    @Test
    fun testStatements() {
        val whileStatement = testCompilation(
            """
            while(x>   4              ) {
               do foo();
               let x = x + 1;
               if(false){
                  let x = 3;
               } else {
                  let x = x + 1;
                  let x = x + 1;
               }
            }
        """.trimIndent(), JackAnalyizerDSL::compileWhileStatement
        )
        assertEquals((whileStatement.condition.term as VarNameTerm).value, "x")
        assertEquals(whileStatement.condition.terms[0].first, Operator.GREATER_THAN)
        assertEquals((whileStatement.condition.terms[0].second as IntegerTerm).value, 4)
        assertEquals(whileStatement.statements.statements.count(), 3)
        assertEquals((whileStatement.statements.statements[0] as DoStatement).subroutineCall.functionName, "foo")
        assertEquals((whileStatement.statements.statements[1] as LetStatement).varName, "x")
        assertEquals((whileStatement.statements.statements[2] as IfStatement).trueStatements.statements.count(), 1)
        assertEquals((whileStatement.statements.statements[2] as IfStatement).falseStatements!!.statements.count(), 2)
    }
}
