package compiler

import compiler.tokenizer.IdentifierToken
import compiler.tokenizer.KeywordToken
import compiler.tokenizer.SymbolToken
import compiler.tokenizer.Token

class JackDSL(private val tokens: List<Token>) {
    private var index = 0
    val compiledCodeAsXML = mutableListOf<String>()
    var indentationLevel: Int = 0

    // this is just a hacky addon to build the XML the course wants
    fun <T> inTag(tag: String, foo: JackDSL.() -> T): T {
        val indentation = "\t".repeat(indentationLevel)
        compiledCodeAsXML.add("$indentation<$tag> ")
        indentationLevel++
        val t = foo()
        indentationLevel--
        compiledCodeAsXML.add("$indentation</$tag>")
        return t
    }

    fun consumeSymbol(expected: Symbol): SymbolToken {
        return consumeSymbol(listOf(expected))
    }

    fun consumeSymbol(): SymbolToken {
        return consumeSymbol(listOf())
    }

    fun consumeSymbol(acceptableSymbols: Collection<Symbol>): SymbolToken {
        val token = tokens[index]
        if (token is SymbolToken && (acceptableSymbols.isEmpty() || acceptableSymbols.contains(token.symbol))) {
            consumeToken()
            return token
        }
        throw CompilationError("Expected ${acceptableSymbols.map(Symbol::value)} but found $token")
    }

    fun consumeKeyword(keyword: Keyword): KeywordToken {
        return consumeKeyword(listOf(keyword))
    }

    fun consumeKeyword(acceptableKeywords: Collection<Keyword>): KeywordToken {
        val token = tokens[index]
        if (token !is KeywordToken || !acceptableKeywords.contains(token.keyword)) {
            throw CompilationError("Expected $acceptableKeywords but found $token")
        }
        consumeToken()
        return token
    }

    fun consumeIdentifier(): IdentifierToken {
        val token = tokens[index]
        if (token !is IdentifierToken) {
            throw CompilationError("Expected Identifier but found $token")
        }
        consumeToken()
        return token
    }

    fun consumeToken(): Token {
        val token = tokens[index]
        compiledCodeAsXML.add("\t".repeat(indentationLevel) + token.toString())
        index++
        return token
    }

    fun peak(): Token {
        return tokens[index]
    }

    fun backpaddle() {
        index--
        compiledCodeAsXML.removeLast()
    }

    fun <T> analyze(x: JackDSL.() -> T): T {
        return this.x()
    }


}

