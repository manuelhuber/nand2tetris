package compiler

open class CompilationError(mes: String) : Exception(mes)
class NotAStatement(s: String) : CompilationError(s)
