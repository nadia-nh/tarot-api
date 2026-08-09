package com.flowworks.tarotapi.data

import com.flowworks.tarotapi.model.TarotCard
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

object CardRepository {

    private val cards: List<TarotCard> by lazy {
        val json = Json { ignoreUnknownKeys = true }
        val text = CardRepository::class.java
            .getResourceAsStream("/cards.json")!!
            .bufferedReader()
            .readText()
        json.decodeFromString(ListSerializer(TarotCard.serializer()), text)
    }

    fun getAll(suit: String? = null): List<TarotCard> =
        if (suit == null) cards else cards.filter { it.suit == suit }

    fun getById(id: String): TarotCard? =
        cards.firstOrNull { it.id == id }
}
