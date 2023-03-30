import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.netty.protocol.http.server.HttpServer
import model.*
import rx.Observable

private var driver = ReactiveMongoDriver()

fun main() {
    HttpServer
        .newServer(8080)
        .start { request, response ->
            val action = request.decodedPath.substring((request.decodedPath.lastIndexOf("/") + 1))
            response.writeString(run {
                var result = Observable.just("")
                runCatching {
                    result = action.handleMapping(request.queryParameters)
                }.onFailure {
                    result = Observable.just(it.message)
                    response.status = HttpResponseStatus.BAD_REQUEST
                }
                result
            })
        }.awaitShutdown()
}

private fun String.handleMapping(queryParameters: Map<String, List<String>>): Observable<String> {
    return when (this) {
        "register" -> handleRegistration(queryParameters)
        "add-product" -> handleAddProduct(queryParameters)
        "get-products" -> handleGetProducts(queryParameters)
        else -> throw RuntimeException("Incorrect command $this")
    }
}

private fun handleRegistration(queryParameters: Map<String, List<String>>): Observable<String> {
    val id = queryParameters[ID]?.first()?.toInt() ?: return Observable.just("No id")
    val currency = queryParameters[CURRENCY]?.first() ?: return Observable.just("No currency")
    val user = User(id, currency)
    return driver.addUser(user).map { "$user added" }
}

private fun handleAddProduct(queryParameters: Map<String, List<String>>): Observable<String> {
    val name = queryParameters[NAME]?.first() ?: return Observable.just("No name")
    val value = queryParameters[VALUE]?.first()?.toDouble() ?: return Observable.just("No value")
    val currency = queryParameters[CURRENCY]?.first() ?: return Observable.just("No currency")

    val product = Product(name, toRub(value, currency), currency)
    return driver.addProduct(product).map { "$product added" }
}

private fun handleGetProducts(queryParameters: Map<String, List<String>>): Observable<String> {
    val id = queryParameters[ID]?.first()?.toInt() ?: return Observable.just("No id")
    return driver.getUser(id).map(User::currency)
        .flatMap { curr -> driver.getProducts().map { pr -> Product(pr.name, toCurrency(pr.value, curr), curr) } }
        .map { it.toString() }
}
