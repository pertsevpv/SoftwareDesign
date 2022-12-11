package token

enum class TokenType {

    LEFT_BRACE, RIGHT_BRACE,
    NUMBER,
    PLUS, MINUS,
    MUL, DIV;

    val priority: Int
        get() = when (this) {
            LEFT_BRACE, RIGHT_BRACE -> 0
            PLUS, MINUS -> 1
            MUL, DIV -> 2
            else -> 3
        }
}