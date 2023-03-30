package model

import CURRENCY
import NAME
import VALUE
import org.bson.Document

data class Product(
    val name: String,
    val value: Double,
    val currency: String
) {

    constructor(doc: Document) : this(
        doc.getString(NAME),
        doc.getDouble(VALUE),
        doc.getString(CURRENCY)
    )

    fun toDocument() = Document(mapOf(NAME to name, VALUE to value, CURRENCY to currency))
}