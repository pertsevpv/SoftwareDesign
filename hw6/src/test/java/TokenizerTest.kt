import org.assertj.core.api.Assertions
import org.junit.Test
import token.NumberToken
import token.OperationToken
import token.TokenType
import token.BraceToken
import java.text.ParseException

class TokenizerTest {

    private val tokenizer = Tokenizer()

    @Test
    fun `test number token`() {
        Assertions.assertThat(tokenizer.parse("12345"))
            .containsExactly(
                NumberToken(12345)
            )
    }

    @Test
    fun `test operations`() {
        Assertions.assertThat(tokenizer.parse("1 + 2 * 3 - 4 / 5"))
            .containsExactly(
                NumberToken(1),
                OperationToken(TokenType.PLUS),
                NumberToken(2),
                OperationToken(TokenType.MUL),
                NumberToken(3),
                OperationToken(TokenType.MINUS),
                NumberToken(4),
                OperationToken(TokenType.DIV),
                NumberToken(5)
            )
    }

    @Test
    fun `test braces`() {
        Assertions.assertThat(tokenizer.parse("(1 + 2)"))
            .containsExactly(
                BraceToken(TokenType.LEFT_BRACE),
                NumberToken(1),
                OperationToken(TokenType.PLUS),
                NumberToken(2),
                BraceToken(TokenType.RIGHT_BRACE)
            )
    }

    @Test
    fun `test more braces`() {
        Assertions.assertThat(tokenizer.parse("(1 + (2 * (3 - (4 / 5))))"))
            .containsExactly(
                BraceToken(TokenType.LEFT_BRACE),
                NumberToken(1),
                OperationToken(TokenType.PLUS),
                BraceToken(TokenType.LEFT_BRACE),
                NumberToken(2),
                OperationToken(TokenType.MUL),
                BraceToken(TokenType.LEFT_BRACE),
                NumberToken(3),
                OperationToken(TokenType.MINUS),
                BraceToken(TokenType.LEFT_BRACE),
                NumberToken(4),
                OperationToken(TokenType.DIV),
                NumberToken(5),
                BraceToken(TokenType.RIGHT_BRACE),
                BraceToken(TokenType.RIGHT_BRACE),
                BraceToken(TokenType.RIGHT_BRACE),
                BraceToken(TokenType.RIGHT_BRACE)
            )
    }

    @Test(expected = ParseException::class)
    fun `test wrong`() {
        tokenizer.parse("2!")
    }
}