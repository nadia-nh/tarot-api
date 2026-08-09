package com.flowworks.tarotapi.routes

import com.flowworks.tarotapi.model.CardListResponse
import com.flowworks.tarotapi.model.ErrorResponse
import com.flowworks.tarotapi.model.TarotCard
import com.flowworks.tarotapi.plugins.configureRouting
import com.flowworks.tarotapi.plugins.configureSerialization
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CardRoutesTest {

    private fun ApplicationTestBuilder.jsonClient() = createClient {
        install(ContentNegotiation) { json() }
    }

    private fun testApp(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        application {
            configureSerialization()
            configureRouting()
        }
        block()
    }

    @Test
    fun `health check returns ok`() = testApp {
        val response = client.get("/health")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("""{"status":"ok"}""", response.bodyAsText())
    }

    @Test
    fun `list all cards returns 78`() = testApp {
        val response = jsonClient().get("/api/v1/cards")

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<CardListResponse>()
        assertEquals(78, body.count)
        assertEquals(78, body.cards.size)
    }

    @Test
    fun `filtering by suit returns only that suit`() = testApp {
        val response = jsonClient().get("/api/v1/cards?suit=wands")

        val body = response.body<CardListResponse>()
        assertEquals(14, body.count)
        assertTrue(body.cards.all { it.suit == "wands" })
    }

    @Test
    fun `get by id returns the matching card`() = testApp {
        val response = jsonClient().get("/api/v1/cards/the_fool")

        assertEquals(HttpStatusCode.OK, response.status)
        val card = response.body<TarotCard>()
        assertEquals("The Fool", card.name)
        assertEquals("major_arcana", card.suit)
    }

    @Test
    fun `get by unknown id returns 404 with error body`() = testApp {
        val response = jsonClient().get("/api/v1/cards/not-a-card")

        assertEquals(HttpStatusCode.NotFound, response.status)
        val error = response.body<ErrorResponse>()
        assertEquals("Card not found: not-a-card", error.error)
    }
}
