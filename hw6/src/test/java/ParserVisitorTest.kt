import org.assertj.core.api.Assertions
import token.NumberToken
import token.OperationToken
import token.TokenType
import token.BraceToken
import org.junit.Before
import org.junit.Test

class ParserVisitorTest {

    private lateinit var parserVisitor: ParserVisitor

    @Before
    fun setup() {
        parserVisitor = ParserVisitor()
    }

    @Test
    fun `test number`() {
        Assertions.assertThat(
            parserVisitor.parse(
                listOf(NumberToken(12345))
            )
        ).containsExactly(NumberToken(12345))
    }

    @Test
    fun `test operations`() {
        Assertions.assertThat(
            parserVisitor.parse(
                listOf(
                    NumberToken(1),
                    OperationToken(TokenType.PLUS),
                    NumberToken(2)
                )
            )
        ).containsExactly(
            NumberToken(1),
            NumberToken(2),
            OperationToken(TokenType.PLUS)
        )
    }

    @Test
    fun `test priority`() {
        Assertions.assertThat(
            parserVisitor.parse(
                listOf(
                    NumberToken(1),
                    OperationToken(TokenType.PLUS),
                    NumberToken(2),
                    OperationToken(TokenType.MUL),
                    NumberToken(3)
                )
            )
        ).containsExactly(
            NumberToken(1),
            NumberToken(2),
            NumberToken(3),
            OperationToken(TokenType.MUL),
            OperationToken(TokenType.PLUS)
        )
    }

    @Test
    fun `test braces`() {
        Assertions.assertThat(
            parserVisitor.parse(
                listOf(
                    BraceToken(TokenType.LEFT_BRACE),
                    NumberToken(1),
                    OperationToken(TokenType.PLUS),
                    NumberToken(2),
                    BraceToken(TokenType.RIGHT_BRACE),
                    OperationToken(TokenType.MUL),
                    NumberToken(3)
                )
            )
        ).containsExactly(
            NumberToken(1),
            NumberToken(2),
            OperationToken(TokenType.PLUS),
            NumberToken(3),
            OperationToken(TokenType.MUL)
        )
    }
}