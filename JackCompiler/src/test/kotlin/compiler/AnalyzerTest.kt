package compiler

import compiler.models.ArrayVarNameTerm
import compiler.models.ExpressionTerm
import compiler.models.IntegerTerm
import compiler.models.UnaryTerm
import compiler.tokenizer.Tokenizer
import org.junit.Test
import kotlin.test.assertEquals

internal class AnalyzerTest {
    @Test
    fun testArrayTerm() {
        val tokens = Tokenizer().tokenize(listOf("foo[1]"))
        val term = Analyzer(tokens).compileTerm()
        assertEquals((term as ArrayVarNameTerm).value, "foo")
        assertEquals((term.ex.term as IntegerTerm).value, 1)
    }

    @Test
    fun testUnaryExpressionTerm() {
        val tokens = Tokenizer().tokenize(listOf("-(4 + 3 * 10)"))
        val term = Analyzer(tokens).compileTerm()
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
}
