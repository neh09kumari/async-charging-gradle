package com.example.controller

import com.example.model.StartSessionRequest
import com.example.service.AuthorizationQueue
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.startSessionRoute() {
    post("/start-session") {
        val request = call.receive<StartSessionRequest>()

        if (!request.isValid()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid input"))
            return@post
        }

        AuthorizationQueue.queue.trySend(request)

        call.respond(HttpStatusCode.Accepted, mapOf(
            "status" to "accepted",
            "message" to "Request is being processed asynchronously. The result will be sent to the provided callback URL."
        ))
    }
}
