import token.*
import java.text.ParseException
import java.util.ArrayList

class Tokenizer {
    private lateinit var input: String
    private var curIndex = 0
    private lateinit var state: State

    fun parse(input: String): List<Token> {
        this.input = input.trim()

        curIndex = 0
        state = StartState()
        state.setNextState(this)

        val result: MutableList<Token> = ArrayList()

        while (state !is EndState && state !is ErrorState) {
            result.add(state.createToken(this))
            while (!isEndOfInput && isWhiteSpace) nextChar()

            state.setNextState(this)
        }
        if (state is ErrorState) throw ParseException((state as ErrorState).message, 0)

        return result
    }

    fun setState(state: State) {
        this.state = state
    }

    val isEndOfInput: Boolean
        get() = curIndex >= input.length

    private val isWhiteSpace: Boolean
        get() = Character.isWhitespace(curChar)

    val isNumber: Boolean
        get() = Character.isDigit(curChar)

    val isOperationOrBrace: Boolean
        get() {
            val availableSymbols = "+-*/()"
            return availableSymbols.indexOf(curChar) >= 0
        }

    val curChar: Char
        get() = input[curIndex]

    fun nextChar() {
        curIndex++
    }
}