package compiler.models

import compiler.Keyword
import compiler.UnaryOperator

sealed class Term

class IntegerTerm(val value: Int) : Term() {
    override fun toString(): String {
        return "IntegerConstant(value=$value)"
    }
}

class StringTerm(val value: String) : Term() {
    override fun toString(): String {
        return "StringConstant(value='$value')"
    }
}

class KeywordTerm(val value: Keyword) : Term() {
    override fun toString(): String {
        return "KeywordConstant(value='$value')"
    }
}

class VarNameTerm(val value: String) : Term() {
    override fun toString(): String {
        return "VarName(value='$value')"
    }
}

class ArrayVarNameTerm(val value: String, val ex: Expression) : Term() {
    override fun toString(): String {
        return "ArrayVarName(value='$value', ex=$ex)"
    }
}

class ExpressionTerm(val value: Expression) : Term() {
    override fun toString(): String {
        return "ExpressionTerm(value=$value)"
    }
}

class UnaryTerm(val operator: UnaryOperator, val term: Term) : Term() {
    override fun toString(): String {
        return "UnaryTerm(operator=$operator, term=$term)"
    }
}


