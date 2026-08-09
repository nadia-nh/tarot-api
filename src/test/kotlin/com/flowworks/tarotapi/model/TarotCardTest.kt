package com.flowworks.tarotapi.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TarotCardTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val sampleCard = TarotCard(
        id = "the_fool",
        name = "The Fool",
        suit = "major_arcana",
        rank = null,
        value = 0,
        upright = Orientation(
            keywords = listOf("new beginnings", "innocence"),
            meaning = "A youth steps lightly toward the edge of a cliff.",
            reflection = "Where am I being called to take a leap of faith?"
        ),
        reversed = Orientation(
            keywords = listOf("recklessness", "naivety"),
            meaning = "That same carefree spirit can tip into carelessness.",
            reflection = "Where has carelessness cost me recently?"
        )
    )

    @Test
    fun `round-trips through JSON without losing data`() {
        val encoded = json.encodeToString(sampleCard)
        val decoded = json.decodeFromString<TarotCard>(encoded)

        assertEquals(sampleCard, decoded)
    }

    @Test
    fun `decodes a minor arcana card with a non-null rank`() {
        val text = """
            {
              "id": "ace_of_wands",
              "name": "Ace of Wands",
              "suit": "wands",
              "rank": "ace",
              "value": 1,
              "upright": {
                "keywords": ["inspiration"],
                "meaning": "A single spark of inspiration arrives.",
                "reflection": "Which creative pursuit have I been hesitating to begin?"
              },
              "reversed": {
                "keywords": ["delays"],
                "meaning": "That initial spark can fizzle before it really catches.",
                "reflection": "What is blocking me from acting on an exciting idea?"
              }
            }
        """.trimIndent()

        val card = json.decodeFromString<TarotCard>(text)

        assertEquals("ace", card.rank)
        assertEquals("wands", card.suit)
        assertEquals(1, card.upright.keywords.size)
    }
}
