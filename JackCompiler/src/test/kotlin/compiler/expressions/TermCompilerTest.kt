package compiler.expressions

import compiler.JackDSL
import compiler.Operator
import compiler.UnaryOperator
import compiler.tokenizer.IntegerConstantToken
import compiler.tokenizer.Tokenizer
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class TermCompilerTest {
    @Test
    fun testArrayTerm() {
        val tokens = Tokenizer().tokenize(listOf("foo[1]"))
        val term = JackDSL(tokens).analyze(JackDSL::compileTerm)
        assertEquals((term as ArrayVarNameTerm).value, "foo")
        assertEquals((term.ex.term as IntegerTerm).value, 1)
    }

    @Test
    fun testUnaryExpressionTerm() {
        val tokens = Tokenizer().tokenize(listOf("-(4 + 3 * 10)"))
        val term = JackDSL(tokens).analyze(JackDSL::compileTerm)
        assertEquals((term as UnaryTerm).operator, UnaryOperator.MINUS)
        assertEquals(((term.term as ExpressionTerm).value.term as IntegerTerm).value, 4)

        assertEquals((term.term as ExpressionTerm).value.terms.count(), 2)

        val additionalTerm = (term.term as ExpressionTerm).value.terms[0]
        assertEquals(additionalTerm.first, Operator.PLUS)
        assertEquals((additionalTerm.second as IntegerTerm).value, 3)

        val secondAdditionalTerm = (term.term as ExpressionTerm).value.terms[1]
        assertEquals(secondAdditionalTerm.first, Operator.STAR)
        assertEquals((secondAdditionalTerm.second as IntegerTerm).value, 10)
    }

    @Test
    fun testExpectedIdentifier() {
        val tokens = Tokenizer().tokenize(listOf("MyClass.1function()"))
        val exception = assertThrows<Exception> {
            JackDSL(tokens).analyze(JackDSL::compileTerm)
        }
        assertEquals(
            exception.message,
            "Expected Identifier but found ${IntegerConstantToken(1)}"
        )
    }
}
