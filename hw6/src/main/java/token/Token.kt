package token

import TokenVisitor
import kotlin.IllegalStateException

sealed interface Token {
    fun accept(visitor: TokenVisitor)
    val tokenType: TokenType
}

data class OperationToken(
    override val tokenType: TokenType
) : Token {

    override fun accept(visitor: TokenVisitor) = visitor.visit(this)

    fun evaluate(a: Int, b: Int) =
        when (tokenType) {
            TokenType.PLUS -> a + b
            TokenType.MINUS -> a - b
            TokenType.MUL -> a * b
            TokenType.DIV -> a / b
            else -> throw IllegalStateException()
        }
}

data class NumberToken(
    val value: Int
) : Token {

    override fun accept(visitor: TokenVisitor) = visitor.visit(this)

    override val tokenType
        get() = TokenType.NUMBER
}

data class BraceToken(
    override val tokenType: TokenType
) : Token {

    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
}