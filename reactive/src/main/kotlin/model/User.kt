package model

import CURRENCY
import ID
import org.bson.Document


data class User(
    val id: Int,
    val currency: String
) {
    constructor(document: Document) : this(document.getInteger(ID), document.getString(CURRENCY))

    fun toDocument() = Document(mapOf(ID to id, CURRENCY to currency))
}