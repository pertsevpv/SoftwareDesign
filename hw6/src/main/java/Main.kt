import java.util.*

fun main() {

    Scanner(System.`in`).runCatching {
        this.use { sc ->
            var input: String

            val tokenizer = Tokenizer()
            val printVisitor = PrintVisitor()
            val calcVisitor = CalcVisitor()

            while (sc.nextLine().also { input = it }.isNotEmpty()) {
                val parserVisitor = ParserVisitor()
                val tokens = tokenizer.parse(input)
                val rpnTokens = parserVisitor.parse(tokens)

                println(printVisitor.print(tokens))
                println(printVisitor.print(rpnTokens))
                println(calcVisitor.calculate(rpnTokens))
            }
        }
    }.onFailure { e ->
        e.printStackTrace()
    }
}
