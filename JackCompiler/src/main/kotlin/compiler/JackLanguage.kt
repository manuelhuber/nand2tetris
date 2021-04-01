package compiler

const val CLASS: String = "class"
const val CONSTRUCTOR: String = "constructor"
const val FUNCTION: String = "function"
const val METHOD: String = "method"
const val FIELD: String = "field"
const val STATIC: String = "static"
const val VAR: String = "var"
const val INT: String = "int"
const val CHAR: String = "char"
const val BOOLEAN: String = "boolean"
const val VOID: String = "void"
const val TRUE: String = "true"
const val FALSE: String = "false"
const val NULL: String = "null"
const val THIS: String = "this"
const val LET: String = "let"
const val DO: String = "do"
const val IF: String = "if"
const val ELSE: String = "else"
const val WHILE: String = "while"
const val RETURN: String = "return"

val KEYWORDS = hashSetOf(
    CLASS,
    CONSTRUCTOR,
    FUNCTION,
    METHOD,
    FIELD,
    STATIC,
    VAR,
    INT,
    CHAR,
    BOOLEAN,
    VOID,
    TRUE,
    FALSE,
    NULL,
    THIS,
    LET,
    DO,
    IF,
    ELSE,
    WHILE,
    RETURN
)

const val CURLY_BRACKET_OPEN = '{'
const val CURLY_BRACKET_CLOSE = '}'
const val ROUND_BRACKET_OPEN = '('
const val ROUND_BRACKET_CLOSE = ')'
const val SQUARE_BRACKET_OPEN = '['
const val SQUARE_BRACKET_CLOSE = ']'
const val DOT = '.'
const val COMMA = ','
const val SEMICOLON = ';'
const val PLUS = '+'
const val MINUS = '-'
const val STAR = '*'
const val SLASH = '/'
const val AMPERSAND = '&'
const val PIPE = '|'
const val LESS_THAN = '<'
const val MORE_THAN = '>'
const val EQUAL = '='
const val NEGATE = '~'

val SYMBOLS = hashSetOf(
    CURLY_BRACKET_OPEN,
    CURLY_BRACKET_CLOSE,
    ROUND_BRACKET_OPEN,
    ROUND_BRACKET_CLOSE,
    SQUARE_BRACKET_OPEN,
    SQUARE_BRACKET_CLOSE,
    DOT,
    COMMA,
    SEMICOLON,
    PLUS,
    MINUS,
    STAR,
    SLASH,
    AMPERSAND,
    PIPE,
    LESS_THAN,
    MORE_THAN,
    EQUAL,
    NEGATE
)
