import token.*
import java.util.Deque
import java.lang.UnsupportedOperationException
import java.util.ArrayDeque
import java.util.ArrayList

sealed interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: BraceToken)
    fun visit(token: OperationToken)
}

class ParserVisitor : TokenVisitor {

    private val tokens: MutableList<Token> = ArrayList()
    private val stack: Deque<Token> = ArrayDeque()

    fun parse(tokens: List<Token>): List<Token> {
        tokens.forEach { it.accept(this) }
        while (!stack.isEmpty()) this.tokens.add(stack.pollLast())

        return ArrayList(this.tokens)
    }

    override fun visit(token: NumberToken) {
        tokens.add(token)
    }

    override fun visit(token: BraceToken) {
        if (token.tokenType == TokenType.LEFT_BRACE) {
            stack.add(token)
        } else {
            var nextToken = stack.pollLast()
            while (
                stack.isNotEmpty() &&
                nextToken.tokenType != TokenType.LEFT_BRACE
            ) {
                tokens.add(nextToken)
                nextToken = stack.pollLast()
            }
        }
    }

    override fun visit(token: OperationToken) {
        if (!stack.isEmpty()) {
            var nextToken = stack.peekLast()
            while (
                stack.isNotEmpty() &&
                token.tokenType.priority <= nextToken.tokenType.priority
            ) {
                tokens.add(stack.pollLast())
                nextToken = stack.peekLast()
            }
        }
        stack.add(token)
    }
}

class PrintVisitor : TokenVisitor {

    var builder = StringBuilder()

    fun print(tokens: List<Token>): String {
        tokens.forEach { it.accept(this) }

        val result = builder.toString()
        builder.clear()
        return result
    }

    override fun visit(token: NumberToken) = print(token)

    override fun visit(token: BraceToken) = print(token)

    override fun visit(token: OperationToken) = print(token)

    private fun print(token: Token) {
        builder.append(token).append(" ")
    }

}

class CalcVisitor : TokenVisitor {

    private val stack: Deque<Int> = ArrayDeque()

    fun calculate(tokens: List<Token>): Int {
        if (tokens.isEmpty()) return 0
        tokens.forEach { it.accept(this) }

        val result = stack.pollLast()
        if (stack.isNotEmpty()) throw IllegalStateException("Too many tokens")
        return result
    }

    override fun visit(token: NumberToken) {
        stack.add(token.value)
    }

    override fun visit(token: BraceToken) = throw UnsupportedOperationException()

    override fun visit(token: OperationToken) {
        if (stack.isEmpty()) throw IllegalStateException("Not enough tokens")
        val b = stack.pollLast()
        if (stack.isEmpty()) throw IllegalStateException("Not enough tokens")
        val a = stack.pollLast()
        stack.add(token.evaluate(a, b))
    }
}
