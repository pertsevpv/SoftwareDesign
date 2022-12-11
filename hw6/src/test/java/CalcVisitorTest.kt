import org.assertj.core.api.Assertions
import token.NumberToken
import token.OperationToken
import token.TokenType
import token.BraceToken
import java.lang.IllegalStateException
import org.junit.Test

class CalcVisitorTest {

    private val calcVisitor = CalcVisitor()

    @Test
    fun `test number`() {
        Assertions.assertThat(
            calcVisitor.calculate(
                listOf(NumberToken(12345))
            )
        ).isEqualTo(12345)
    }

    @Test
    fun `test operations`() {
        Assertions.assertThat(
            calcVisitor.calculate(
                listOf(
                    NumberToken(5),
                    NumberToken(4),
                    OperationToken(TokenType.PLUS),
                    NumberToken(3),
                    NumberToken(2),
                    OperationToken(TokenType.MUL),
                    NumberToken(1),
                    OperationToken(TokenType.DIV),
                    OperationToken(TokenType.MINUS)
                )
            )
        ).isEqualTo(3)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `test one brace`() {
        calcVisitor.calculate(listOf(BraceToken(TokenType.LEFT_BRACE)))
    }

    @Test(expected = IllegalStateException::class)
    fun `test extra token`() {
        val res = calcVisitor.calculate(
            listOf(
                NumberToken(1),
                NumberToken(2)
            )
        )
        println(res)
    }

    @Test(expected = IllegalStateException::class)
    fun `test not enough token`() {
        calcVisitor.calculate(
            listOf(
                NumberToken(1),
                OperationToken(TokenType.PLUS)
            )
        )
    }
}