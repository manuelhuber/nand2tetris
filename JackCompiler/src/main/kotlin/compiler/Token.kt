package compiler

open class Token(val value: String) {

    override fun toString(): String {
        val simpleName = this::javaClass.get().simpleName.toLowerCase()
        return "<$simpleName> $value </$simpleName>"
    }
}

class Keyword(value: String) : Token(value)
class Symbol(value: String) : Token(value)
class IntegerConstant(value: String) : Token(value)
class StringConstant(value: String) : Token(value)
class Identifier(value: String) : Token(value)
