package com.example

import com.example.controller.startSessionRoute
import com.example.service.AuthorizationWorker
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.server.routing.*

fun main() {
    AuthorizationWorker.start()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }
        routing {
            startSessionRoute()
        }
    }.start(wait = true)


}
