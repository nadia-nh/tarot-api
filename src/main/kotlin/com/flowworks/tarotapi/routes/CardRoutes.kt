package com.flowworks.tarotapi.routes

import com.flowworks.tarotapi.data.CardRepository
import com.flowworks.tarotapi.model.CardListResponse
import com.flowworks.tarotapi.model.ErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cardRoutes() {
    route("/api/v1/cards") {
        get {
            val suit = call.request.queryParameters["suit"]
            val cards = CardRepository.getAll(suit)
            call.respond(CardListResponse(count = cards.size, cards = cards))
        }
        get("/{id}") {
            val id = call.parameters["id"]!!
            val card = CardRepository.getById(id)
            if (card == null) {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("Card not found: $id"))
            } else {
                call.respond(card)
            }
        }
    }
}
