import token.*
import java.lang.StringBuilder
import java.lang.UnsupportedOperationException

sealed interface State {
    fun createToken(tokenizer: Tokenizer): Token
    fun setNextState(tokenizer: Tokenizer)
}

class StartState : State {

    override fun createToken(tokenizer: Tokenizer): Token {
        val c = tokenizer.curChar
        tokenizer.nextChar()

        return when (c) {
            '(' -> BraceToken(TokenType.LEFT_BRACE)
            ')' -> BraceToken(TokenType.RIGHT_BRACE)
            '+' -> OperationToken(TokenType.PLUS)
            '-' -> OperationToken(TokenType.MINUS)
            '*' -> OperationToken(TokenType.MUL)
            '/' -> OperationToken(TokenType.DIV)
            else -> throw Exception()
        }
    }

    override fun setNextState(tokenizer: Tokenizer) {
        if (tokenizer.isEndOfInput)
            tokenizer.setState(EndState())
        else if (tokenizer.isNumber)
            tokenizer.setState(NumberState())
        else if (tokenizer.isOperationOrBrace)
            tokenizer.setState(StartState())
        else
            tokenizer.setState(ErrorState("Unexpected character : " + tokenizer.curChar))
    }
}

class NumberState : State {

    override fun createToken(tokenizer: Tokenizer): Token {
        val value = StringBuilder()
        while (!tokenizer.isEndOfInput && Character.isDigit(tokenizer.curChar)) {
            value.append(tokenizer.curChar)
            tokenizer.nextChar()
        }
        return NumberToken(value.toString().toInt())
    }

    override fun setNextState(tokenizer: Tokenizer) {
        if (tokenizer.isEndOfInput)
            tokenizer.setState(EndState())
        else if (tokenizer.isOperationOrBrace)
            tokenizer.setState(StartState())
        else
            tokenizer.setState(ErrorState("Unexpected symbol : " + tokenizer.curChar))
    }
}

class ErrorState(
    val message: String
) : State {
    override fun createToken(tokenizer: Tokenizer): Token {
        throw UnsupportedOperationException()
    }

    override fun setNextState(tokenizer: Tokenizer) {
        throw UnsupportedOperationException()
    }
}

class EndState : State {

    override fun createToken(tokenizer: Tokenizer): Token {
        throw UnsupportedOperationException()
    }

    override fun setNextState(tokenizer: Tokenizer) {
        throw UnsupportedOperationException()
    }
}