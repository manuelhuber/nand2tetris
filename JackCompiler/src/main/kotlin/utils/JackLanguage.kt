package utils

enum class Keyword(val value: String) {
    CLASS("class"),
    CONSTRUCTOR("constructor"),
    FUNCTION("function"),
    METHOD("method"),
    FIELD("field"),
    STATIC("static"),
    VAR("var"),
    INT("int"),
    CHAR("char"),
    BOOLEAN("boolean"),
    VOID("void"),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    THIS("this"),
    LET("let"),
    DO("do"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    RETURN("return");

    companion object {
        fun fromValue(keyword: String) = values().first { symbol -> symbol.value == keyword }
    }
}

val KEYWORDS = Keyword.values().map { keyword -> keyword.value }.toHashSet()

enum class Symbol(val value: Char) {
    CURLY_BRACKET_OPEN('{'),
    CURLY_BRACKET_CLOSE('}'),
    ROUND_BRACKET_OPEN('('),
    ROUND_BRACKET_CLOSE(')'),
    SQUARE_BRACKET_OPEN('['),
    SQUARE_BRACKET_CLOSE(']'),
    DOT('.'),
    COMMA(','),
    SEMICOLON(';'),
    PLUS('+'),
    MINUS('-'),
    STAR('*'),
    SLASH('/'),
    AMPERSAND('&'),
    PIPE('|'),
    LESS_THAN('<'),
    MORE_THAN('>'),
    EQUAL('='),
    NEGATE('~');

    companion object {
        fun fromValue(char: Char) = values().first { symbol -> symbol.value == char }
    }
}

val SYMBOLS = Symbol.values().map { keyword -> keyword.value }.toHashSet()

enum class Operator(val value: Symbol) {
    PLUS(Symbol.PLUS),
    MINUS(Symbol.MINUS),
    STAR(Symbol.STAR),
    SLASH(Symbol.SLASH),
    AMPERSAND(Symbol.AMPERSAND),
    PIPE(Symbol.PIPE),
    LESS_THAN(Symbol.LESS_THAN),
    GREATER_THAN(Symbol.MORE_THAN),
    EQUAL(Symbol.EQUAL);

    companion object {
        fun fromValue(symbol: Symbol) = values().first { op -> op.value == symbol }
    }
}

val OPERATORS = Operator.values().map { keyword -> keyword.value.value }.toHashSet()

enum class UnaryOperator(val value: Symbol) {
    MINUS(Symbol.MINUS),
    NEGATE(Symbol.NEGATE);

    companion object {
        fun fromValue(symbol: Symbol) = values().first { op -> op.value == symbol }
    }
}

val UNARY_OPERATORS = UnaryOperator.values().map { keyword -> keyword.value }.toHashSet()
