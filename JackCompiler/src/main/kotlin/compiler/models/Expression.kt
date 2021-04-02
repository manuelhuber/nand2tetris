package compiler.models

import compiler.Operator

class Expression(val term: Term, val terms: List<Pair<Operator, Term>> = listOf())

