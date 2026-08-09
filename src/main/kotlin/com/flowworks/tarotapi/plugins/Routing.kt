package com.flowworks.tarotapi.plugins

import com.flowworks.tarotapi.routes.cardRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.respondText("""{"status":"ok"}""", ContentType.Application.Json)
        }
        cardRoutes()
    }
}
