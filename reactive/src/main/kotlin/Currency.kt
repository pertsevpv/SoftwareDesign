val currencyMap =
    mapOf(
        "RUB" to 1.0,
        "USD" to 1.0 / 30.0,
        "EUR" to 1.0 / 40.0,
    )

fun toCurrency(value: Double, toCurrency: String) : Double {
    val coef = currencyMap[toCurrency] ?: 1.0
    return value * coef
}

fun toRub(value: Double, fromCurrency: String) : Double {
    val coef = currencyMap[fromCurrency] ?: 1.0
    return value / coef
}